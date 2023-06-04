package com.example.finalproject.DBUtils;


import android.util.Log;

import com.example.finalproject.Callbacks.AdminsCallback;
import com.example.finalproject.Callbacks.AreasCallback;
import com.example.finalproject.Callbacks.SystemParamCallback;
import com.example.finalproject.Callbacks.VotersCallback;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Area;
import com.example.finalproject.Entities.Date;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Voter;
import com.example.finalproject.Callbacks.PartiesCallback;
import com.example.finalproject.Entities.VoterVote;
import com.mongodb.internal.async.SingleResultCallback;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;


public class DbUtils {
    private MongoClient mongoClient;
    private User user;
    private App app;
    private String AppId = "finalproject-wnxuy";

    public DbUtils() {
    }

    ////////////////////////////ALL COLLECTIONS//////////////////////////
    public void deleteCollection(String dataBase, String collectionName) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document();
        collection.deleteMany(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                long count = task.get().getDeletedCount();
                if (count != 0) {
                    Log.v("EXAMPLE", "successfully deleted " + count + " documents.");
                } else {
                    Log.v("EXAMPLE", "did not delete any documents.");
                }
            } else {
                Log.e("EXAMPLE", "failed to delete documents with: ", task.getError());
            }
        });
    }

    ////////////////////////////VOTER/////////////////////////////////////
    public void initConnection() {
        app = new App(new AppConfiguration.Builder(AppId).build());

        if (app.currentUser() == null) {
            app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
                @Override
                public void onResult(App.Result<User> result) {
                    if (result.isSuccess()) {
                        Log.d("User", "Logged In Successfully");
                        initializeMongoDB();
                    } else {
                        Log.d("User", "Failed to Login");
                    }
                }
            });
        } else {
            initializeMongoDB();
        }
    }

    private void initializeMongoDB() {
        user = app.currentUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
    }

    public void addVotersToDb(String dataBase, String collectionName, List<Voter> voters) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        List<Document> documents = new ArrayList<>();
        for (Voter voter : voters) {
            documents.add(new Document("voterId", voter.getVoterId())
                    .append("firstName", voter.getFirstName())
                    .append("lastName", voter.getLastName())
                    .append("age", voter.getAge())
                    .append("gender", voter.getGender().toString())
                    .append("area", voter.getArea())
                    .append("alreadyVote", voter.isAlreadyVote())
                    .append("idNumber", voter.getIdNumber())
                    .append("phoneNumber", voter.getPhoneNumber()));
        }
        collection.insertMany(documents).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
        ;
    }

    public void addVoterToDb(String dataBase, String collectionName, Voter voter) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = new Document("voterId", voter.getVoterId())
                .append("firstName", voter.getFirstName())
                .append("lastName", voter.getLastName())
                .append("age", voter.getAge())
                .append("gender", voter.getGender().toString())
                .append("area", voter.getArea())
                .append("alreadyVote", voter.isAlreadyVote())
                .append("idNumber", voter.getIdNumber())
                .append("phoneNumber", voter.getPhoneNumber());
        collection.insertOne(document).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
    }

    public void sumOfAllVoters(String dataBase, String collectionName, SingleResultCallback<Integer> callback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        AtomicLong count = new AtomicLong();
        collection.count().getAsync(task -> {
            if (task.isSuccess()) {
                count.set(task.get());
                callback.onResult(count.intValue(), null);
            } else {
                callback.onResult(null, task.getError());
                Log.e("ptttttt", "failed to count documents with: ", task.getError());
            }
        });
    }

    public void getVoterById(String dataBase, String collectionName, String id, SingleResultCallback<Voter> callback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("idNumber", Integer.valueOf(id));
        AtomicReference<Voter> tempVoter = new AtomicReference<>();
        collection.findOne(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Document result = task1.get();
                // Assuming that Voter is a class that can be constructed from a Document.
                if (result == null) {
                    callback.onResult(null, null);
                } else {
                    tempVoter.set(new Voter(result));
                    callback.onResult(tempVoter.get(), null);
                    Log.v("EXAMPLE", "successfully found a document: " + task1);
                }
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                callback.onResult(null, task1.getError());
            }
        });
    }

    public void getVoterByVoterId(String dataBase, String collectionName, String voterId, SingleResultCallback<Voter> callback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("voterId", voterId);
        AtomicReference<Voter> tempVoter = new AtomicReference<>();
        collection.findOne(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Document result = task1.get();
                // Assuming that Voter is a class that can be constructed from a Document.
                tempVoter.set(new Voter(result));
                callback.onResult(tempVoter.get(), null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                callback.onResult(null, task1.getError());
            }
        });
    }


    public void getPhoneNumberById(String dataBase, String collectionName, String id, SingleResultCallback<String> callback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("idNumber", Integer.valueOf(id));
        AtomicReference<Voter> tempVoter = new AtomicReference<>();
        collection.findOne(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Document result = task1.get();
                // Assuming that Voter is a class that can be constructed from a Document.
                if(result == null){
                    callback.onResult(null, null);
                }else{
                    tempVoter.set(new Voter(result));
                    callback.onResult(tempVoter.get().getPhoneNumber(), null);
                    Log.v("EXAMPLE", "successfully found a document: " + task1);
                }
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                callback.onResult(null, task1.getError());
            }
        });
    }

    public void getAllVoters(String dataBase, String collectionName, VotersCallback votersCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document();
        RealmResultTask<MongoCursor<Document>> findTask = collection.find(queryFilter).iterator();
        findTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                Log.v("EXAMPLE", "successfully found all plants for Store 42:");
                List<Voter> voters = new ArrayList<>();
                while (results.hasNext()) {
                    Document doc = results.next();
                    Voter voter = new Voter(doc);
                    voters.add(voter);
                    Log.v("EXAMPLE", doc.toString());
                }
                votersCallback.onComplete(voters, null);
            } else {
                votersCallback.onComplete(null, task.getError());
                Log.e("EXAMPLE", "failed to find documents with: ", task.getError());
            }
        });
    }

    public void updateVoterHowAlreadyVote(String dataBase, String collectionName, String userId) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("voterId", userId);
        Document updateDocument = new Document("$set", new Document("alreadyVote", true));
        collection.updateOne(queryFilter, updateDocument).getAsync(task -> {
            if (task.isSuccess()) {
                long count = task.get().getModifiedCount();
                if (count == 1) {
                    Log.v("EXAMPLE", "successfully updated a document.");
                } else {
                    Log.v("EXAMPLE", "did not update a document.");
                }
            } else {
                Log.e("EXAMPLE", "failed to update document with: ", task.getError());
            }
        });
    }

    public void getAllVotersAllCountry(String dataBase, String collectionName, VotersCallback votersCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        Document queryFilter = new Document();

        collection.count(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Long result = task1.get();
                //tempVote.set(new Vote(result));
                votersCallback.onComplete(result, null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                votersCallback.onComplete(null, task1.getError());
            }
        });
    }

    public void getAllVotersInArea(String dataBase, String collectionName, String area, VotersCallback votersCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        Document queryFilter = new Document("area", area);

        collection.count(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Long result = task1.get();
                //tempVote.set(new Vote(result));
                votersCallback.onComplete(result, null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                votersCallback.onComplete(null, task1.getError());
            }
        });
    }

////////////////////////////PARTY/////////////////////////////////////

    public void addParties(String dataBase, String collectionName, List<Party> parties) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        List<Document> documents = new ArrayList<>();
        for (Party party : parties) {
            documents.add(new Document("partyId", party.getPartyId())
                    .append("name", party.getName())
                    .append("agenda", party.getAgenda())
                    .append("logoResourceId", party.getLogoResourceId()));
        }
        collection.insertMany(documents).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
    }

    public void addParty(String dataBase, String collectionName, Party party) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = new Document(new Document("partyId", party.getPartyId())
                .append("name", party.getName())
                .append("agenda", party.getAgenda())
                .append("logoResourceId", party.getLogoResourceId()));
        collection.insertOne(document).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
    }

    public void sumOfAllParties(String dataBase, String collectionName, SingleResultCallback<Integer> callback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        AtomicLong count = new AtomicLong();
        collection.count().getAsync(task -> {
            if (task.isSuccess()) {
                count.set(task.get());
                callback.onResult(count.intValue(), null);
            } else {
                callback.onResult(null, task.getError());
                Log.e("ptttttt", "failed to count documents with: ", task.getError());
            }
        });
    }

    public void getPartyByPartyId(String dataBase, String collectionName, String partyId, SingleResultCallback<Party> callback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("partyId", partyId);
        AtomicReference<Party> tempParty = new AtomicReference<>();
        collection.findOne(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Document result = task1.get();
                // Assuming that Voter is a class that can be constructed from a Document.
                tempParty.set(new Party(result));
                callback.onResult(tempParty.get(), null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                callback.onResult(null, task1.getError());
            }
        });
    }

    public void getAllParties(String dataBase, String collectionName, PartiesCallback partiesCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document();
        RealmResultTask<MongoCursor<Document>> findTask = collection.find(queryFilter).iterator();
        findTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                Log.v("EXAMPLE", "successfully found all plants for Store 42:");
                List<Party> parties = new ArrayList<>();
                while (results.hasNext()) {
                    Document doc = results.next();
                    Party party = new Party(doc);
                    parties.add(party);
                    Log.v("EXAMPLE", doc.toString());
                }
                partiesCallback.onComplete(parties, null);
            } else {
                partiesCallback.onComplete(null, task.getError());
                Log.e("EXAMPLE", "failed to find documents with: ", task.getError());
            }
        });
    }

////////////////////////////AREAS/////////////////////////////////////

    public void addArea(String dataBase, String collectionName, Area area) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = new Document(new Document("areaId", area.getId())
                .append("name", area.getAreaName()).append("defaultVoteStation", area.getDefaultVoteStation()));
        collection.insertOne(document).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
    }

    public void addAllArea(String dataBase, String collectionName, List<Area> areas) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        List<Document> documents = new ArrayList<>();

        for (Area area : areas) {
            Document document = new Document(new Document("areaId", area.getId())
                    .append("name", area.getAreaName()).append("defaultVoteStation", area.getDefaultVoteStation()));
            documents.add(document);
        }

        collection.insertMany(documents).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
    }

    public void getAllAreas(String dataBase, String collectionName, AreasCallback areasCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document();
        RealmResultTask<MongoCursor<Document>> findTask = collection.find(queryFilter).iterator();
        findTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                Log.v("EXAMPLE", "successfully found all plants for Store 42:");
                List<Area> areas = new ArrayList<>();
                while (results.hasNext()) {
                    Document doc = results.next();
                    Area area = new Area(doc);
                    areas.add(area);
                    Log.v("EXAMPLE", doc.toString());
                }
                areasCallback.onComplete(areas, null);
            } else {
                areasCallback.onComplete(null, task.getError());
                Log.e("EXAMPLE", "failed to find documents with: ", task.getError());
            }
        });
    }

    public void getAreaByAreaName(String dataBase, String collectionName, String areaName, AreasCallback areasCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        Document queryFilter = new Document("name", areaName);
        AtomicReference<Area> tempArea = new AtomicReference<>();
        collection.findOne(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Document result = task1.get();
                tempArea.set(new Area(result));
                areasCallback.onComplete(tempArea.get(), null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                areasCallback.onComplete(null, task1.getError());
            }
        });
    }

    ////////////////////////////VOTES/////////////////////////////////////
    public void addNewVote(String dataBase, String collectionName, VoterVote voterVote) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = new Document(new Document("partyId", voterVote.getPartyId())
                .append("area", voterVote.getArea()).append("age", voterVote.getAge())
                .append("gender", voterVote.getGender()));
        collection.insertOne(document).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
    }

    public void getAllVotesInCountry(String dataBase, String collectionName, VotersCallback votersCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        Document queryFilter = new Document();

        collection.count(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Long result = task1.get();
                //tempVote.set(new Vote(result));
                votersCallback.onComplete(result, null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                votersCallback.onComplete(null, task1.getError());
            }
        });
    }

    public void getAllVotesInArea(String dataBase, String collectionName, String area, VotersCallback votersCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        Document queryFilter = new Document("area", area);

        collection.count(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Long result = task1.get();
                //tempVote.set(new Vote(result));
                votersCallback.onComplete(result, null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                votersCallback.onComplete(null, task1.getError());
            }
        });
    }

    public void getAllAgeVoterByArea(String dataBase, String collectionName, String area, int dropDownSelection, VotersCallback votersCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        List<Document> pipeline = Arrays.asList(
                // Filter documents by the "city" field
                new Document("$match", new Document("area", area)),
                new Document("$match", new Document("age", new Document("$gte", 18))),
                new Document("$addFields", new Document("rangeStart", new Document("$subtract", Arrays.asList("$age", new Document("$mod", Arrays.asList("$age", dropDownSelection)))))),
                new Document("$group", new Document("_id", "$rangeStart")
                        .append("count", new Document("$sum", 1))),
                new Document("$sort", new Document("_id", 1))
        );


// query mongodb using the pipeline
        RealmResultTask<MongoCursor<Document>> aggregationTask =
                collection.aggregate(pipeline).iterator();
// handle success or failure of the query
        aggregationTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                // iterate over and print the results to the log
                Map<Integer, Integer> temp = new HashMap<>();
                while (results.hasNext()) {
                    Document doc = results.next();
                    int rangeStart = doc.getInteger("_id");
                    int count = doc.getInteger("count");
                    temp.put(rangeStart, count);
                }
                votersCallback.onComplete(temp, null);
                Log.v("EXAMPLE", "successfully found a document: " + task);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
                votersCallback.onComplete(null, task.getError());
            }
        });
    }

    public void getAllAgeVoter(String dataBase, String collectionName, int dropDownSelection, VotersCallback votersCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        List<Document> pipeline = Arrays.asList(
                new Document("$match", new Document("age", new Document("$gte", 18))),
                new Document("$addFields", new Document("rangeStart", new Document("$subtract", Arrays.asList("$age", new Document("$mod", Arrays.asList("$age", dropDownSelection)))))),
                new Document("$group", new Document("_id", "$rangeStart")
                        .append("count", new Document("$sum", 1))),
                new Document("$sort", new Document("_id", 1))
        );

// query mongodb using the pipeline
        RealmResultTask<MongoCursor<Document>> aggregationTask =
                collection.aggregate(pipeline).iterator();
// handle success or failure of the query
        aggregationTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                // iterate over and print the results to the log
                Map<Integer, Integer> temp = new HashMap<>();
                while (results.hasNext()) {
                    Document doc = results.next();
                    int rangeStart = doc.getInteger("_id");
                    int count = doc.getInteger("count");
                    temp.put(rangeStart, count);
                }
                votersCallback.onComplete(temp, null);
                Log.v("EXAMPLE", "successfully found a document: " + task);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
                votersCallback.onComplete(null, task.getError());
            }
        });
    }


    public void getAllAgeVoterHowVoteByArea(String dataBase, String collectionName, String area, int dropDownSelection, VotersCallback votersCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        List<Document> pipeline = Arrays.asList(
                // Filter documents by the "city" and "alreadyVote" fields
                new Document("$match", new Document("area", area).append("alreadyVote", true)),
                new Document("$match", new Document("age", new Document("$gte", 18))),
                new Document("$addFields", new Document("rangeStart", new Document("$subtract", Arrays.asList("$age", new Document("$mod", Arrays.asList("$age", dropDownSelection)))))),
                new Document("$group", new Document("_id", "$rangeStart")
                        .append("count", new Document("$sum", 1))),
                new Document("$sort", new Document("_id", 1))
        );


// query mongodb using the pipeline
        RealmResultTask<MongoCursor<Document>> aggregationTask =
                collection.aggregate(pipeline).iterator();
// handle success or failure of the query
        aggregationTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                // iterate over and print the results to the log
                Map<Integer, Integer> temp = new HashMap<>();
                while (results.hasNext()) {
                    Document doc = results.next();
                    int rangeStart = doc.getInteger("_id");
                    int count = doc.getInteger("count");
                    temp.put(rangeStart, count);
                }
                votersCallback.onComplete(temp, null);
                Log.v("EXAMPLE", "successfully found a document: " + task);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
                votersCallback.onComplete(null, task.getError());
            }
        });
    }

    public void getAllAgeVoterHowVote(String dataBase, String collectionName, int dropDownSelection, VotersCallback votersCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        List<Document> pipeline = Arrays.asList(
                new Document("$match", new Document("age", new Document("$gte", 18)).append("alreadyVote", true)),
                new Document("$addFields", new Document("rangeStart", new Document("$subtract", Arrays.asList("$age", new Document("$mod", Arrays.asList("$age", dropDownSelection)))))),
                new Document("$group", new Document("_id", "$rangeStart")
                        .append("count", new Document("$sum", 1))),
                new Document("$sort", new Document("_id", 1))
        );


// query mongodb using the pipeline
// query mongodb using the pipeline
        RealmResultTask<MongoCursor<Document>> aggregationTask =
                collection.aggregate(pipeline).iterator();
// handle success or failure of the query
        aggregationTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                // iterate over and print the results to the log
                Map<Integer, Integer> temp = new HashMap<>();
                while (results.hasNext()) {
                    Document doc = results.next();
                    int rangeStart = doc.getInteger("_id");
                    int count = doc.getInteger("count");
                    temp.put(rangeStart, count);
                }
                votersCallback.onComplete(temp, null);
                Log.v("EXAMPLE", "successfully found a document: " + task);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
                votersCallback.onComplete(null, task.getError());
            }
        });
    }

    public void getAllVotesAndSex(String dataBase, String collectionName, String sex, VotersCallback votersCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        Document queryFilter = new Document("gender", sex);

        collection.count(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Long result = task1.get();
                //tempVote.set(new Vote(result));
                votersCallback.onComplete(result, null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                votersCallback.onComplete(null, task1.getError());
            }
        });
    }

    public void getAllVotesInAreaAndSex(String dataBase, String collectionName, String area, String sex, VotersCallback votersCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        Document queryFilter = new Document("area", area);
        queryFilter.append("gender", sex);


        collection.count(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Long result = task1.get();
                //tempVote.set(new Vote(result));
                votersCallback.onComplete(result, null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                votersCallback.onComplete(null, task1.getError());
            }
        });
    }

    ////////////////////////////ADMIN////////////////////////////////////
    public void addAdminLeader(String dataBase, String collectionName, Admin admin) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = new Document("voterId", admin.getVoterId())
                .append("area", admin.getArea())
                .append("isAdminLeader", admin.isAdminLeader());
        collection.insertOne(document).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
    }

    public void manageAdmin(String dataBase, String collectionName, String voterId, Admin admin) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = new Document("voterId", admin.getVoterId())
                .append("area", admin.getArea())
                .append("isAdminLeader", admin.isAdminLeader());
        collection.insertOne(document).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
    }

    public void fireAdmin(String dataBase, String collectionName, String voterId) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("voterId", voterId);
        collection.deleteOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                long count = task.get().getDeletedCount();
                if (count == 1) {
                    Log.v("EXAMPLE", "successfully deleted a document.");
                } else {
                    Log.v("EXAMPLE", "did not delete a document.");
                }
            } else {
                Log.e("EXAMPLE", "failed to delete document with: ", task.getError());
            }
        });
    }

    public void getAllAdmins(String dataBase, String collectionName, AdminsCallback adminsCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document();
        RealmResultTask<MongoCursor<Document>> findTask = collection.find(queryFilter).iterator();
        findTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                Log.v("EXAMPLE", "successfully found all plants for Store 42:");
                List<Admin> admins = new ArrayList<>();
                while (results.hasNext()) {
                    Document doc = results.next();
                    String voterId = doc.getString("voterId");
                    Voter voter = TemporaryDB.getVoterById(voterId);
                    Admin admin = new Admin(voterId, doc, voter);
                    admins.add(admin);
                    Log.v("EXAMPLE", doc.toString());
                }
                adminsCallback.onComplete(admins, null);
            } else {
                adminsCallback.onComplete(null, task.getError());
                Log.e("EXAMPLE", "failed to find documents with: ", task.getError());
            }
        });
    }


    public void getResultAllCountry(String dataBase, String collectionName, PartiesCallback partiesCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        List<Document> pipeline = Arrays.asList(

                new Document("$sortByCount", "$partyId"),
                new Document("$lookup", new Document("from", "parties")
                        .append("localField", "_id")
                        .append("foreignField", "partyId")
                        .append("as", "theParty")),
                new Document("$project", new Document("count", "$count")
                        .append("partyName", "$theParty.name")));

        RealmResultTask<MongoCursor<Document>> aggregationTask =
                collection.aggregate(pipeline).iterator();
        aggregationTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                // iterate over and print the results to the log
                Map<String, Integer> parties = new HashMap<>();
                while (results.hasNext()) {
                    Document doc = results.next();
                    parties.put(String.valueOf(((ArrayList<Document>) doc.get("partyName")).get(0)), (Integer) doc.get("count"));
                    Log.v("EXAMPLE", doc.toString());
                }
                partiesCallback.onComplete(parties, null);
            } else {
                partiesCallback.onComplete(null, task.getError());
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
                // votersCallback.onComplete(null, task.getError());
            }
        });
    }

    public void getResultByArea(String dataBase, String collectionName, String area, PartiesCallback partiesCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        List<Document> pipeline = Arrays.asList(
                new Document("$match", new Document("area", area)),
                new Document("$sortByCount", "$partyId"),
                new Document("$lookup", new Document("from", "parties")
                        .append("localField", "_id")
                        .append("foreignField", "partyId")
                        .append("as", "theParty")),
                new Document("$project", new Document("count", "$count")
                        .append("partyName", "$theParty.name")));

        RealmResultTask<MongoCursor<Document>> aggregationTask =
                collection.aggregate(pipeline).iterator();
        aggregationTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                // iterate over and print the results to the log
                Map<String, Integer> parties = new HashMap<>();
                while (results.hasNext()) {
                    Document doc = results.next();
                    parties.put(String.valueOf(((ArrayList<Document>) doc.get("partyName")).get(0)), (Integer) doc.get("count"));
                    Log.v("EXAMPLE", doc.toString());
                }
                partiesCallback.onComplete(parties, null);
            } else {
                partiesCallback.onComplete(null, task.getError());
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
                // votersCallback.onComplete(null, task.getError());
            }
        });
    }

    public void getAllAdminsAndThereInheritors(String dataBase, String collectionName, AdminsCallback adminsCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> adminsCollection = database.getCollection(collectionName);

        List<Document> pipeline = Arrays.asList(
                new Document("$lookup", new Document("from", "voters")
                        .append("localField", "voterId")
                        .append("foreignField", "voterId")
                        .append("as", "AdminInherbit")),
                new Document("$project", new Document("adminSide",
                        new Document("voterId", "$voterId")
                                .append("area", "$area")
                                .append("isAdminLeader", "$isAdminLeader"))
                        .append("voterSide",
                                new Document("$sortArray",
                                        new Document("input", "$AdminInherbit")
                                                .append("sortBy", new Document("_id", 1.0))))));

        RealmResultTask<MongoCursor<Document>> aggregationTask =
                adminsCollection.aggregate(pipeline).iterator();
        aggregationTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                // iterate over and print the results to the log
                List<Admin> admins = new ArrayList<>();
                while (results.hasNext()) {
                    Document doc = results.next();
                    Admin temp = new Admin((Document) doc.get("adminSide"), (ArrayList<Document>) doc.get("voterSide"));
                    admins.add(temp);
                    Log.v("EXAMPLE", doc.toString());
                }
                adminsCallback.onComplete(admins, null);
            } else {
                adminsCallback.onComplete(null, task.getError());
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
                // votersCallback.onComplete(null, task.getError());
            }
        });
    }

    public void getAdminByVoterId(String dataBase, String collectionName, String voterId, SingleResultCallback<Admin> callback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("voterId", voterId);
        AtomicReference<Admin> tempAdmin = new AtomicReference<>();
        collection.findOne(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Document result = task1.get();
                if (result == null) {
                    callback.onResult(null, null);
                } else {
                    tempAdmin.set(new Admin(result));
                    callback.onResult(tempAdmin.get(), null);
                    Log.v("EXAMPLE", "successfully found a document: " + task1);
                }
                // Assuming that Voter is a class that can be constructed from a Document.
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                callback.onResult(null, task1.getError());
            }
        });
    }


    //////////////////////////SYSTEM_PARAMS/////////////////////////

    public void getValueByKey(String dataBase, String collectionName, String key, SystemParamCallback systemParamCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("key", key);
        collection.findOne(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Document result = task1.get();
                systemParamCallback.onComplete(result.get("value"), null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                systemParamCallback.onComplete(null, task1.getError());
            }
        });
    }

    public void getValueByKeyDateObject(String dataBase, String collectionName, String key, SystemParamCallback systemParamCallback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("key", key);
        AtomicReference<Date> tempDate = new AtomicReference<>();
        collection.findOne(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Document result = task1.get();
                result = (Document) result.get("value");
                tempDate.set(new Date(result));
                systemParamCallback.onComplete(tempDate.get(), null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                systemParamCallback.onComplete(null, task1.getError());
            }
        });
    }
}
