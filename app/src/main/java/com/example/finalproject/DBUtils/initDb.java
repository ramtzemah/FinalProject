package com.example.finalproject.DBUtils;

import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Area;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Voter;
import com.example.finalproject.Enums.Gender;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class initDb {
    private DbUtils dbUtils;
    public initDb() throws InterruptedException {
        dbUtils = new DbUtils();
        dbUtils.deleteCollection(Constant.DataBaseName,Constant.VotersCollection);
        dbUtils.deleteCollection(Constant.DataBaseName,Constant.PartiesCollection);
        dbUtils.deleteCollection(Constant.DataBaseName,Constant.AreasCollection);
        dbUtils.deleteCollection(Constant.DataBaseName,Constant.AdminsCollection);
        dbUtils.deleteCollection(Constant.DataBaseName,Constant.VotesCollectionNew);
        Thread.sleep(5000);

        addVotersToDB();
        Thread.sleep(5000);

        addPartiesToDB();
        Thread.sleep(5000);

        addAreasToDB();
        Thread.sleep(5000);

        addAdminToDB();
        Thread.sleep(5000);

    }

    public static void addVotersToDB(){
        List<Voter> voters = new ArrayList<>();
        voters.add(new Voter("עומר","גאן",19, Gender.זכר,"צפון",208447649,"+972509219009"));
        voters.add(new Voter("רם","צמח",20, Gender.נקבה,"דרום",205965866,"+972509219009"));
        voters.add(new Voter("שון","בללי",18, Gender.זכר,"מזרח",206321846,"+972509219009"));
        voters.add(new Voter("שחף","אליס",21, Gender.נקבה,"דרום",315962522,"+972509219009"));
        voters.add(new Voter("מתן","שושן",27, Gender.זכר,"צפון",205697477,"+972509219009"));
        voters.add(new Voter("עידן","פרץ",28, Gender.נקבה,"מזרח",316425164,"+972509219009"));
        voters.add(new Voter("שימי","גאן",49, Gender.זכר,"צפון",24673485,"+972509219009"));
        voters.add(new Voter("אסתר","כלפון",20, Gender.נקבה,"דרום",29402112,"+972509219009"));
        voters.add(new Voter("עידן","גור",29, Gender.נקבה,"מזרח",324950211,"+972509219009"));
        voters.add(new Voter("עילאי","גיר",57, Gender.זכר,"צפון",222471922,"+972509219009"));
        voters.add(new Voter("ניצה","אלפסי",35, Gender.נקבה,"דרום",28510626,"+972509219009"));
        voters.add(new Voter("ספיר","אליהו",43, Gender.זכר,"מזרח",311310148,"+972509219009"));
        voters.add(new Voter("רעות","דוד",52, Gender.זכר,"צפון",300596319,"+972509219009"));
        voters.add(new Voter("גלית","גוטמן",60, Gender.נקבה,"דרום",31589831,"+972509219009"));
        voters.add(new Voter("ירדן","רוטמן",55, Gender.נקבה,"מזרח",327836136,"+972509219009"));
        voters.add(new Voter("אילנה","פרידמן",39, Gender.זכר,"מערב",31589823,"+972509219009"));
        DbUtils dbUtils = new DbUtils();
        dbUtils.addVotersToDb(Constant.DataBaseName, Constant.VotersCollection, voters);
    }

    public static void addPartiesToDB(){
        List<Party> parties = new ArrayList<>();
        parties.add((new Party("מפלגת החיות", R.drawable.ic_animals_logo,"דוגלת במדיניות המקדמת רווחת בעלי חיים וחקיקה חזקה יותר לזכויות בעלי חיים, כגון איסור על ניסויים בבעלי חיים למטרות קוסמטיות, הגדלת העונשים על התעללות בבעלי חיים ושיפור תקני רווחת בעלי חיים בחקלאות ובייצור מזון.")));
        parties.add((new Party("מפלגת אחדות", R.drawable.ic_ahdot_logo,"דוגלת במדיניות המקדמת דו-קיום בדרכי שלום בין ישראלים לפלסטינים, כגון עידוד דיאלוג ומשא ומתן, תמיכה בפתרון שתי המדינות וקידום יוזמות בין-דתיות ותרבותיות.")));
        parties.add((new Party("מפלגת המהגרים", R.drawable.ic_hagira_logo,"דוגלת במדיניות התומכת ומשלבת מהגרים בחברה הישראלית, כגון הגברת הגישה לשיעורי שפה ותכניות הכשרה בעבודה, מתן סיוע בדיור ורפורמה בחוקי ההגירה כך שיהיו הוגנים ומכילים יותר.")));
        parties.add((new Party("מפלגת המורשת", R.drawable.ic_lagacy_logo," דוגל במדיניות המקדמת ומשמרת את המורשת התרבותית וההיסטורית של ישראל, כגון הגדלת המימון למוסדות ואירועי תרבות, הגנה על אתרים היסטוריים וקידום תיירות.")));
        parties.add((new Party("המפלגה נגד שחיתות", R.drawable.ic_negedshitot_logo,"דוגלת במדיניות המקדמת שקיפות ואחריות בממשלה, כגון יישום רפורמת מימון קמפיינים, חיזוק חוקי ההגנה על חושפי שחיתויות ויצירת ועדה עצמאית נגד שחיתות.")));
        parties.add((new Party("מפלגת החילוניים", R.drawable.ic_secular_logo,"דוגלת במדיניות המקדמת חילוניות והפרדת דת ומדינה בכל היבטי החברה הישראלית, כגון הפסקת מימון המדינה למוסדות דת, הסרת ההשפעה הדתית ממערכת החינוך וביטול חוקי הנישואין והגירושין הדתיים.")));
        parties.add((new Party("המפלגה לעסקים קטנים", R.drawable.ic_smallbuis_logo,"תומכים במדיניות התומכת בעסקים קטנים וביזמים, כגון הפחתת הרגולציות והביורוקרטיה הביורוקרטית, מתן הקלות מס לעסקים קטנים והגדלת המימון לתוכניות הלוואות לעסקים קטנים.")));
        parties.add((new Party("המפלגה הטכנולוגית", R.drawable.ic_techno_logo,"דוגל במדיניות המקדמת חדשנות טכנולוגית ויזמות, כגון מתן תמריצי מס ומימון לסטארטאפים, השקעה במחקר ופיתוח וקידום חינוך (STEM Science, Technology, Engineering ו-Mathematics) בבתי ספר.")));
        parties.add((new Party("המפלגה הירוקה", R.drawable.ic_yeroka_logo,"תומכים במדיניות שמפחיתה את פליטת הפחמן ומקדמת אנרגיה מתחדשת, כמו השקעה באנרגיה סולרית ורוח, בניית יותר תחבורה ציבורית והגדלת מסים על דלקים מאובנים. כמו כן, לעודד חקלאות בת קיימא יותר ולהפחית את השימוש בפלסטיק.")));
        parties.add((new Party("מפלגת הצעירים", R.drawable.ic_youngs_logo,"דוגלת במדיניות המיטיבה עם הדורות הצעירים, כגון שיפור הגישה לדיור בר השגה, הגדלת המימון לתוכניות חינוך והכשרה בעבודה, והפחתת עלות שירותי הבריאות.")));
        DbUtils dbUtils = new DbUtils();
        dbUtils.addParties(Constant.DataBaseName,Constant.PartiesCollection,parties);
    }

    public void addAreasToDB(){
                        List<Area> areas = new ArrayList<>();
                        areas.add(new Area("צפון","מתנס מרכז הגליל"));
                        areas.add(new Area("דרום","באר שבעת בית הספר דרורים"));
                        areas.add(new Area("מזרח","מרכז ירושלים"));
                        areas.add(new Area("מערב","חוף האמצע"));
                        dbUtils.addAllArea(Constant.DataBaseName,Constant.AreasCollection, areas);
                    }

    public void addAdminToDB(){
        Voter tempVoter = new Voter("צצ","צצ",39, Gender.זכר,"מזרח",17,"+972509219009");
        dbUtils.addVoterToDb(Constant.DataBaseName, Constant.VotersCollection, tempVoter);
        dbUtils.addAdminLeader(Constant.DataBaseName, Constant.AdminsCollection,new Admin(tempVoter, "all",true));
    }

}
