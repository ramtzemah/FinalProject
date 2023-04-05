package com.example.finalproject.Calculations;

import com.example.finalproject.DBUtils.TemporaryDB;
import com.example.finalproject.Entities.Admin;
import com.example.finalproject.Entities.Area;
import com.example.finalproject.Entities.Party;
import com.example.finalproject.Entities.Voter;
import com.example.finalproject.Enums.Gender;
import com.example.finalproject.R;

import java.util.UUID;

public class Generators {

    public static String generateId(){
        String uniqueID = UUID.randomUUID().toString();
        System.out.println(uniqueID);
        return uniqueID;
    }

    public static void addVotersToDB(){
        TemporaryDB.addVoter(new Voter("aaa","aaa",19, Gender.MALE,"Netanya",1,"0"));
        TemporaryDB.addVoter(new Voter("bbb","bbb",20, Gender.FEMALE,"Hedera",2,"0"));
        TemporaryDB.addVoter(new Voter("ccc","ccc",18, Gender.MALE,"Tel Aviv",3,"0"));
        TemporaryDB.addVoter(new Voter("ddd","ddd",21, Gender.FEMALE,"Hedera",4,"0"));
        TemporaryDB.addVoter(new Voter("eee","eee",27, Gender.MALE,"Netanya",5,"0"));
        TemporaryDB.addVoter(new Voter("fff","fff",28, Gender.FEMALE,"Tel Aviv",6,"0"));
        TemporaryDB.addVoter(new Voter("ggg","ggg",49, Gender.MALE,"Netanya",7,"0"));
        TemporaryDB.addVoter(new Voter("hhh","hhh",20, Gender.FEMALE,"Hedera",8,"0"));
        TemporaryDB.addVoter(new Voter("iii","iii",29, Gender.FEMALE,"Tel Aviv",9,"0"));
        TemporaryDB.addVoter(new Voter("jjj","jjj",57, Gender.MALE,"Netanya",10,"0"));
        TemporaryDB.addVoter(new Voter("kkk","kkk",35, Gender.FEMALE,"Hedera",11,"0"));
        TemporaryDB.addVoter(new Voter("lll","lll",43, Gender.MALE,"Tel Aviv",12,"0"));
        TemporaryDB.addVoter(new Voter("mmm","mmm",52, Gender.MALE,"Netanya",13,"0"));
        TemporaryDB.addVoter(new Voter("nnn","nnn",60, Gender.FEMALE,"Hedera",14,"0"));
        TemporaryDB.addVoter(new Voter("ooo","ooo",55, Gender.FEMALE,"Tel Aviv",15,"0"));
        TemporaryDB.addVoter(new Voter("ppp","ppp",39, Gender.MALE,"Eilat",16,"0"));
    }

    public static void addPartiesToDB(){
        TemporaryDB.addParty((new Party("מפלגת החיות",R.drawable.ic_animals_logo,"דוגלת במדיניות המקדמת רווחת בעלי חיים וחקיקה חזקה יותר לזכויות בעלי חיים, כגון איסור על ניסויים בבעלי חיים למטרות קוסמטיות, הגדלת העונשים על התעללות בבעלי חיים ושיפור תקני רווחת בעלי חיים בחקלאות ובייצור מזון.")));
        TemporaryDB.addParty((new Party("מפלגת אחדות", R.drawable.ic_ahdot_logo,"דוגלת במדיניות המקדמת דו-קיום בדרכי שלום בין ישראלים לפלסטינים, כגון עידוד דיאלוג ומשא ומתן, תמיכה בפתרון שתי המדינות וקידום יוזמות בין-דתיות ותרבותיות.")));
        TemporaryDB.addParty((new Party("מפלגת המהגרים", R.drawable.ic_hagira_logo,"דוגלת במדיניות התומכת ומשלבת מהגרים בחברה הישראלית, כגון הגברת הגישה לשיעורי שפה ותכניות הכשרה בעבודה, מתן סיוע בדיור ורפורמה בחוקי ההגירה כך שיהיו הוגנים ומכילים יותר.")));
        TemporaryDB.addParty((new Party("מפלגת המורשת", R.drawable.ic_lagacy_logo," דוגל במדיניות המקדמת ומשמרת את המורשת התרבותית וההיסטורית של ישראל, כגון הגדלת המימון למוסדות ואירועי תרבות, הגנה על אתרים היסטוריים וקידום תיירות.")));
        TemporaryDB.addParty((new Party("המפלגה נגד שחיתות", R.drawable.ic_negedshitot_logo,"דוגלת במדיניות המקדמת שקיפות ואחריות בממשלה, כגון יישום רפורמת מימון קמפיינים, חיזוק חוקי ההגנה על חושפי שחיתויות ויצירת ועדה עצמאית נגד שחיתות.")));
        TemporaryDB.addParty((new Party("מפלגת החילוניים", R.drawable.ic_secular_logo,"דוגלת במדיניות המקדמת חילוניות והפרדת דת ומדינה בכל היבטי החברה הישראלית, כגון הפסקת מימון המדינה למוסדות דת, הסרת ההשפעה הדתית ממערכת החינוך וביטול חוקי הנישואין והגירושין הדתיים.")));
        TemporaryDB.addParty((new Party("המפלגה לעסקים קטנים", R.drawable.ic_smallbuis_logo,"תומכים במדיניות התומכת בעסקים קטנים וביזמים, כגון הפחתת הרגולציות והביורוקרטיה הביורוקרטית, מתן הקלות מס לעסקים קטנים והגדלת המימון לתוכניות הלוואות לעסקים קטנים.")));
        TemporaryDB.addParty((new Party("המפלגה הטכנולוגית", R.drawable.ic_techno_logo,"דוגל במדיניות המקדמת חדשנות טכנולוגית ויזמות, כגון מתן תמריצי מס ומימון לסטארטאפים, השקעה במחקר ופיתוח וקידום חינוך (STEM Science, Technology, Engineering ו-Mathematics) בבתי ספר.")));
        TemporaryDB.addParty((new Party("המפלגה הירוקה", R.drawable.ic_yeroka_logo,"תומכים במדיניות שמפחיתה את פליטת הפחמן ומקדמת אנרגיה מתחדשת, כמו השקעה באנרגיה סולרית ורוח, בניית יותר תחבורה ציבורית והגדלת מסים על דלקים מאובנים. כמו כן, לעודד חקלאות בת קיימא יותר ולהפחית את השימוש בפלסטיק.")));
        TemporaryDB.addParty((new Party("מפלגת הצעירים", R.drawable.ic_youngs_logo,"דוגלת במדיניות המיטיבה עם הדורות הצעירים, כגון שיפור הגישה לדיור בר השגה, הגדלת המימון לתוכניות חינוך והכשרה בעבודה, והפחתת עלות שירותי הבריאות.")));
    }

    public static void addAreasToDB(){
        TemporaryDB.addArea(new Area("צפון"));
        TemporaryDB.addArea(new Area("דרום"));
        TemporaryDB.addArea(new Area("מזרח"));
        TemporaryDB.addArea(new Area("מערב"));
    }

    public static void addAdminToDB(){
        Voter tempVoter = new Voter("qqq","qqq",39, Gender.MALE,"Eilat",16,"0");
        TemporaryDB.addVoter(tempVoter);
        TemporaryDB.addAdminLeader(new Admin(tempVoter, " ",false));
    }
}
