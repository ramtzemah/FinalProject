package com.example.finalproject;

import android.util.Log;

import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.Callbacks.PartiesCallback;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Area;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Voter;
import com.example.finalproject.Enums.Gender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class initDb {
    private  DbUtils dbUtils;
    public initDb() throws InterruptedException {
        dbUtils = new DbUtils();
        dbUtils.deleteAllVoters(Constant.DataBaseName,Constant.VotersCollection);
        dbUtils.deleteAllTokens(Constant.DataBaseName,Constant.TokensCollection);
        dbUtils.deleteAllVotes(Constant.DataBaseName, Constant.VotesCollection);
        dbUtils.deleteAllParties(Constant.DataBaseName,Constant.PartiesCollection);
        dbUtils.deleteAllAreas(Constant.DataBaseName,Constant.AreasCollection);
        dbUtils.deleteAllAdmins(Constant.DataBaseName,Constant.AdminsCollection);

        Thread.sleep(5000);

        addVotersToDB();
        Thread.sleep(5000);

        addPartiesToDB();
        Thread.sleep(5000);

        initVotesCollection();
        Thread.sleep(5000);

        addAreasToDB();
        Thread.sleep(5000);

        addAdminToDB();
        Thread.sleep(5000);

    }

    public static void addVotersToDB(){
        List<Voter> voters = new ArrayList<>();
        voters.add(new Voter("אאא","אאא",19, Gender.זכר,"צפון",1,"+972503046017"));
        voters.add(new Voter("בבב","בבב",20, Gender.נקבה,"דרום",2,"0504020492"));
        voters.add(new Voter("גגג","גגג",18, Gender.זכר,"מזרח",3,"0504242047"));
        voters.add(new Voter("דדד","דדד",21, Gender.נקבה,"דרום",4,"4"));
        voters.add(new Voter("ההה","ההה",27, Gender.זכר,"צפון",5,"5"));
        voters.add(new Voter("ווו","ווו",28, Gender.נקבה,"מזרח",6,"6"));
        voters.add(new Voter("זזז","זזז",49, Gender.זכר,"צפון",7,"7"));
        voters.add(new Voter("חחח","חחח",20, Gender.נקבה,"דרום",8,"8"));
        voters.add(new Voter("טטט","טטט",29, Gender.נקבה,"מזרח",9,"9"));
        voters.add(new Voter("כככ","כככ",57, Gender.זכר,"צפון",10,"10"));
        voters.add(new Voter("ללל","ללל",35, Gender.נקבה,"דרום",11,"11"));
        voters.add(new Voter("מממ","מממ",43, Gender.זכר,"מזרח",12,"12"));
        voters.add(new Voter("נננ","נננ",52, Gender.זכר,"צפון",13,"13"));
        voters.add(new Voter("ססס","ססס",60, Gender.נקבה,"דרום",14,"14"));
        voters.add(new Voter("עעע","עעע",55, Gender.נקבה,"מזרח",15,"15"));
        voters.add(new Voter("פפפ","פפפ",39, Gender.זכר,"מערב",16,"16"));
        DbUtils dbUtils = new DbUtils();
        dbUtils.addVotersToDb(Constant.DataBaseName, Constant.VotersCollection, voters);
    }

    public void addPartiesToDB(){
        List<Party> parties = new ArrayList<>();
        parties.add((new Party("מפלגת החיות",R.drawable.ic_animals_logo,"דוגלת במדיניות המקדמת רווחת בעלי חיים וחקיקה חזקה יותר לזכויות בעלי חיים, כגון איסור על ניסויים בבעלי חיים למטרות קוסמטיות, הגדלת העונשים על התעללות בבעלי חיים ושיפור תקני רווחת בעלי חיים בחקלאות ובייצור מזון.")));
        parties.add((new Party("מפלגת אחדות", R.drawable.ic_ahdot_logo,"דוגלת במדיניות המקדמת דו-קיום בדרכי שלום בין ישראלים לפלסטינים, כגון עידוד דיאלוג ומשא ומתן, תמיכה בפתרון שתי המדינות וקידום יוזמות בין-דתיות ותרבותיות.")));
        parties.add((new Party("מפלגת המהגרים", R.drawable.ic_hagira_logo,"דוגלת במדיניות התומכת ומשלבת מהגרים בחברה הישראלית, כגון הגברת הגישה לשיעורי שפה ותכניות הכשרה בעבודה, מתן סיוע בדיור ורפורמה בחוקי ההגירה כך שיהיו הוגנים ומכילים יותר.")));
        parties.add((new Party("מפלגת המורשת", R.drawable.ic_lagacy_logo," דוגל במדיניות המקדמת ומשמרת את המורשת התרבותית וההיסטורית של ישראל, כגון הגדלת המימון למוסדות ואירועי תרבות, הגנה על אתרים היסטוריים וקידום תיירות.")));
        parties.add((new Party("המפלגה נגד שחיתות", R.drawable.ic_negedshitot_logo,"דוגלת במדיניות המקדמת שקיפות ואחריות בממשלה, כגון יישום רפורמת מימון קמפיינים, חיזוק חוקי ההגנה על חושפי שחיתויות ויצירת ועדה עצמאית נגד שחיתות.")));
        parties.add((new Party("מפלגת החילוניים", R.drawable.ic_secular_logo,"דוגלת במדיניות המקדמת חילוניות והפרדת דת ומדינה בכל היבטי החברה הישראלית, כגון הפסקת מימון המדינה למוסדות דת, הסרת ההשפעה הדתית ממערכת החינוך וביטול חוקי הנישואין והגירושין הדתיים.")));
        parties.add((new Party("המפלגה לעסקים קטנים", R.drawable.ic_smallbuis_logo,"תומכים במדיניות התומכת בעסקים קטנים וביזמים, כגון הפחתת הרגולציות והביורוקרטיה הביורוקרטית, מתן הקלות מס לעסקים קטנים והגדלת המימון לתוכניות הלוואות לעסקים קטנים.")));
        parties.add((new Party("המפלגה הטכנולוגית", R.drawable.ic_techno_logo,"דוגל במדיניות המקדמת חדשנות טכנולוגית ויזמות, כגון מתן תמריצי מס ומימון לסטארטאפים, השקעה במחקר ופיתוח וקידום חינוך (STEM Science, Technology, Engineering ו-Mathematics) בבתי ספר.")));
        parties.add((new Party("המפלגה הירוקה", R.drawable.ic_yeroka_logo,"תומכים במדיניות שמפחיתה את פליטת הפחמן ומקדמת אנרגיה מתחדשת, כמו השקעה באנרגיה סולרית ורוח, בניית יותר תחבורה ציבורית והגדלת מסים על דלקים מאובנים. כמו כן, לעודד חקלאות בת קיימא יותר ולהפחית את השימוש בפלסטיק.")));
        parties.add((new Party("מפלגת הצעירים", R.drawable.ic_youngs_logo,"דוגלת במדיניות המיטיבה עם הדורות הצעירים, כגון שיפור הגישה לדיור בר השגה, הגדלת המימון לתוכניות חינוך והכשרה בעבודה, והפחתת עלות שירותי הבריאות.")));
        dbUtils.addParties(Constant.DataBaseName,Constant.PartiesCollection,parties);
    }
    public void initVotesCollection(){
        dbUtils.getAllParties(Constant.DataBaseName, Constant.PartiesCollection,(PartiesCallback) (result, error) -> {
                    if (error != null) {
                        Log.d("ptttt", "errorrrrr");
                        // Handle the error.
                    } else {
                        Log.d("ptttt", "" + result);
                        List<Party> parties = (List<Party>) result;
                            dbUtils.initVotes(Constant.DataBaseName,Constant.VotesCollection, parties);
                    }
                }
        );
    }

    public void addAreasToDB(){
        dbUtils.getAllParties(Constant.DataBaseName, Constant.PartiesCollection,(PartiesCallback) (result, error) -> {
                    if (error != null) {
                        Log.d("ptttt", "errorrrrr");
                        // Handle the error.
                    } else {
                        Log.d("ptttt", "" + result);
                        List<Party> parties = (List<Party>) result;
                        List<Area> areas = new ArrayList<>();
                        Set<String> partiesId = new HashSet<>();
                        for (Party party : parties){
                            partiesId.add(party.getPartyId());
                        }
                        areas.add(new Area("צפון", partiesId));
                        areas.add(new Area("דרום", partiesId));
                        areas.add(new Area("מזרח", partiesId));
                        areas.add(new Area("מערב", partiesId));
                        dbUtils.addAllArea(Constant.DataBaseName,Constant.AreasCollection, areas);
                    }
                }
        );

    }

    public void addAdminToDB(){
        Voter tempVoter = new Voter("צצ","צצ",39, Gender.זכר,"אילת",17,"0");
        dbUtils.addVoterToDb(Constant.DataBaseName, Constant.VotersCollection, tempVoter);
        dbUtils.addAdminLeader(Constant.DataBaseName, Constant.AdminsCollection,new Admin(tempVoter, "all",true));
       // TemporaryDB.addAdminLeader(new Admin(tempVoter, " ",true));
    }

}
