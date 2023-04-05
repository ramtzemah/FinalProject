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
        TemporaryDB.addVoter(new Voter("אאא","אאא",19, Gender.זכר,"נתניה",1,"0"));
        TemporaryDB.addVoter(new Voter("בבב","בבב",20, Gender.נקבה,"חדרה",2,"0"));
        TemporaryDB.addVoter(new Voter("גגג","גגג",18, Gender.זכר,"תל-אביב",3,"0"));
        TemporaryDB.addVoter(new Voter("דדד","דדד",21, Gender.נקבה,"חדרה",4,"0"));
        TemporaryDB.addVoter(new Voter("ההה","ההה",27, Gender.זכר,"נתניה",5,"0"));
        TemporaryDB.addVoter(new Voter("ווו","ווו",28, Gender.נקבה,"תל-אביב",6,"0"));
        TemporaryDB.addVoter(new Voter("זזז","זזז",49, Gender.זכר,"נתניה",7,"0"));
        TemporaryDB.addVoter(new Voter("חחח","חחח",20, Gender.נקבה,"חדרה",8,"0"));
        TemporaryDB.addVoter(new Voter("טטט","טטט",29, Gender.נקבה,"תל-אביב",9,"0"));
        TemporaryDB.addVoter(new Voter("כככ","כככ",57, Gender.זכר,"נתניה",10,"0"));
        TemporaryDB.addVoter(new Voter("ללל","ללל",35, Gender.נקבה,"חדרה",11,"0"));
        TemporaryDB.addVoter(new Voter("מממ","מממ",43, Gender.זכר,"תל-אביב",12,"0"));
        TemporaryDB.addVoter(new Voter("נננ","נננ",52, Gender.זכר,"נתניה",13,"0"));
        TemporaryDB.addVoter(new Voter("ססס","ססס",60, Gender.נקבה,"חדרה",14,"0"));
        TemporaryDB.addVoter(new Voter("עעע","עעע",55, Gender.נקבה,"תל-אביב",15,"0"));
        TemporaryDB.addVoter(new Voter("פפפ","פפפ",39, Gender.זכר,"אילת",16,"0"));
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
        TemporaryDB.addArea(new Area("צפון", TemporaryDB.getAllParties().keySet()));
        TemporaryDB.addArea(new Area("דרום", TemporaryDB.getAllParties().keySet()));
        TemporaryDB.addArea(new Area("מזרח", TemporaryDB.getAllParties().keySet()));
        TemporaryDB.addArea(new Area("מערב", TemporaryDB.getAllParties().keySet()));
    }

    public static void addAdminToDB(){
        Voter tempVoter = new Voter("צצ","צצ",39, Gender.זכר,"אילת",16,"0");
        TemporaryDB.addVoter(tempVoter);
        TemporaryDB.addAdminLeader(new Admin(tempVoter, " ",true));
    }
}
