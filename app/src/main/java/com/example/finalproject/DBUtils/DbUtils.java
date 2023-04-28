package com.example.finalproject.DBUtils;

import android.util.Log;

import com.example.finalproject.Callbacks.AdminsCallback;
import com.example.finalproject.Callbacks.AreasCallback;
import com.example.finalproject.Callbacks.VotersCallback;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Area;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Token;
import com.example.finalproject.Entities.Vote;
import com.example.finalproject.Entities.Voter;
import com.example.finalproject.Callbacks.PartiesCallback;
import com.mongodb.internal.async.SingleResultCallback;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
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
////////////////////////////VOTER/////////////////////////////////////
    private void initConnection() {
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
                    .append("city", voter.getCity())
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
                .append("city", voter.getCity())
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
                tempVoter.set(new Voter(result));
                callback.onResult(tempVoter.get().getPhoneNumber(), null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                callback.onResult(null, task1.getError());
            }
        });
    }

    public void deleteAllVoters(String dataBase, String collectionName){
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter  = new Document();
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

    public void deleteAllParties(String dataBase, String collectionName){
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter  = new Document();
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

////////////////////////////AREAS/////////////////////////////////////

    public void addArea(String dataBase, String collectionName, Area area){
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = new Document(new Document("areaId", area.getId())
                .append("name", area.getAreaName()));
        Document votes = new Document();
        for (Map.Entry<String, Integer> entry : area.getPartiesVotes().entrySet()) {
            votes.append(entry.getKey(), entry.getValue());
        }
        document.append("partiesVotes", votes);
        collection.insertOne(document).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
    }

    public void updateAreaWithVotes(String dataBase, String collectionName, Area area) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("areaId", area.getId());

        Document updateDocument = new Document();
        for (Map.Entry<String, Integer> entry : area.getPartiesVotes().entrySet()) {
            updateDocument.append(entry.getKey(), entry.getValue());
        }
        updateDocument.append("name", area.getAreaName()).append("areaId", area.getId());

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


    public void addAllArea(String dataBase, String collectionName, List<Area> areas) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        List<Document> documents = new ArrayList<>();

        for (Area area : areas) {
            Document document = new Document(new Document("areaId", area.getId())
                    .append("name", area.getAreaName()));
            Document votes = new Document();
            for (Map.Entry<String, Integer> entry : area.getPartiesVotes().entrySet()) {
                votes.append(entry.getKey(), entry.getValue());
            }
            document.append("partiesVotes", votes);
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

    public void deleteAllAreas(String dataBase, String collectionName){
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter  = new Document();
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

    public void initVotes(String dataBase, String collectionName, List<Party> parties) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        List<Document> documents = new ArrayList<>();
        for (Party party : parties) {
            documents.add(new Document("partyId", party.getPartyId())
                    .append("votes", 0));
        }
        collection.insertMany(documents).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
    }

    public void deleteAllVotes(String dataBase, String collectionName){
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter  = new Document();
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

    public void getVoteByPartyId(String dataBase, String collectionName, String partyId, SingleResultCallback<Vote> callback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("partyId", partyId);
        AtomicReference<Vote> tempVote = new AtomicReference<>();
        collection.findOne(queryFilter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Document result = task1.get();
                tempVote.set(new Vote(result));
                callback.onResult(tempVote.get(), null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                callback.onResult(null, task1.getError());
            }
        });
    }

    public void addVoteByPartyId(String dataBase, String collectionName, String partyId, int updatedVote) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter = new Document("partyId", partyId);

        Document updateDocument = new Document("$set", new Document("partyId", partyId)
                .append("votes", 1));
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

/////////////////////////////TOKENS/////////////////////////////////////

    public void addToken(String dataBase, String collectionName, Token token) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = new Document(new Document("tokenId", token.getTokenId())
                .append("voterId", token.getVoterId())
                .append("token", token.getToken())
                .append("sentDate", token.getSentDate())
                .append("expirationDate", token.getExpirationDate()));
        collection.insertOne(document).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("ptttttttt", "Inserted successfully");
            } else {
                Log.d("ptttttttt", "Error " + result.getError());
            }
        });
    }

    public void getTokenByVoterId(String dataBase, String collectionName, String voterId, SingleResultCallback<Token> callback) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Bson filter = new Document("voterId", voterId);
        AtomicReference<Token> tempToken = new AtomicReference<>();
        collection.findOne(filter).getAsync(task1 -> {
            if (task1.isSuccess()) {
                Document result = task1.get();
                // Assuming that Voter is a class that can be constructed from a Document.
                tempToken.set(new Token(result));
                callback.onResult(tempToken.get(), null);
                Log.v("EXAMPLE", "successfully found a document: " + task1);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task1.getError());
                callback.onResult(null, task1.getError());
            }
        });
    }

    public void deleteAllTokens(String dataBase, String collectionName){
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter  = new Document();
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
    ////////////////////////////ADMIN////////////////////////////////////
    public void addAdminLeader(String dataBase, String collectionName, Admin admin) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = new Document("id", admin.getId())
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

    public void deleteAllAdmins(String dataBase, String collectionName) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document queryFilter  = new Document();
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

    public void manageAdmin(String dataBase, String collectionName, String voterId, Admin admin) {
        initConnection();
        MongoDatabase database = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = new Document("id", admin.getId())
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
        Document queryFilter = new Document("id", voterId);
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
                    String id = doc.getString("id");
                    Voter voter = TemporaryDB.getVoterById(id);
                    Admin admin = new Admin(id, doc, voter);
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
}
