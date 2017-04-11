package jumapp.com.smartest.Storage.DAOImpl.ContentsImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.AlternativeDAO;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.AttachmentDAO;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.QuestionDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Alternative;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Attachment;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Contest;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;




/**
 * Created by marco on 18/03/2017.
 */
public class QuestionDAOImpl  extends SQLiteOpenHelper implements QuestionDAO {



    public static final String DATABASE_NAME = "Questions.db";
    public static final String CONTACTS_TABLE_NAME = "myQuestions";

    Context context;


    public QuestionDAOImpl(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table \"" + CONTACTS_TABLE_NAME + "\"" +
                        "(question_id integer primary key ,Category text,Text text, isFavorited integer,isStudied integer," +
                        "contest_id integer, hasAttachment integer)"
        );

        // db.execSQL("CREATE INDEX alternative_question_id_index on "+ CONTACTS_TABLE_NAME+ " (contest_id);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS"+CONTACTS_TABLE_NAME);
        onCreate(db);
        db.close();
    }



    public void deleteAll() {
        // TODO Auto-generated method stub
        SQLiteDatabase dbN = this.getWritableDatabase();
        dbN.execSQL("DROP TABLE IF EXISTS \""+CONTACTS_TABLE_NAME+"\"");
        onCreate(dbN);
        dbN.close();
    }

    @Override
    public int numberOfRowsByContest(long contestId){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\" where contest_id='" + contestId + "'", null);
        return res.getCount();
    }

    @Override
    public void insert(Question q,SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();

        int isFavorited=0;
        int isStudied=0;
        int hasAttach=0;

        if(q.getFavorited()) isFavorited=1;
        if(q.getStudied()) isStudied=1;
        if(q.getHasAttach()) hasAttach=1;

        contentValues.put("question_id",  q.getQuestion_id() );
        contentValues.put(" Category",  q.getCategory() );
        contentValues.put("isFavorited",  isFavorited );
        contentValues.put("isStudied ", isStudied);
        contentValues.put("contest_id", q.getContest_id());
        contentValues.put("Text",q.getText());
        contentValues.put("hasAttachment", hasAttach);
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
    }

    @Override
    public ArrayList<Question> getAllQuestionByCategoryAndContestId(long contestId,String categoryParam) {

        long start=  System.currentTimeMillis();;
        AlternativeDAO alt= new AlternativeDAOImpl(context);
        AttachmentDAO att= new AttachmentDAOImpl(context);
        ArrayList<Question> questions = new ArrayList<Question>();
        SQLiteDatabase dbQuest = this.getReadableDatabase();
        SQLiteDatabase dbAlt=alt.openConnection();
        SQLiteDatabase dbAtt=att.openConnection();
        long endInit=  System.currentTimeMillis();;

        Log.i("LLL init TIme: ", "" + (endInit - start));

        Cursor res =  dbQuest.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\" where contest_id='" + contestId + "' AND " +
                "Category='" + categoryParam + "'", null);
        res.moveToFirst();
        long queryTIme=  System.currentTimeMillis();

        Log.i("LLL query TIme: ",""+(queryTIme-endInit));
        long sumAtt=0;
        long sumAlt=0;


        while(res.isAfterLast() == false) {

            long question_id = Long.parseLong(res.getString(res.getColumnIndex("question_id")));
            String category = res.getString(res.getColumnIndex("Category"));
            String text = res.getString(res.getColumnIndex("Text"));

            int isFavoritedint = Integer.parseInt(res.getString(res.getColumnIndex("isFavorited")));
            boolean isFavorited = false;
            if (isFavoritedint == 1) isFavorited = true;

            int isStudiedint = Integer.parseInt(res.getString(res.getColumnIndex("isStudied")));
            boolean isStudied = false;
            if (isStudiedint == 1) isStudied = true;

            boolean hasAttachment=false;
            int intHasAttachment=Integer.parseInt(res.getString(res.getColumnIndex("hasAttachment")));
            if(intHasAttachment==1) hasAttachment=true;

            long contest_id = Long.parseLong(res.getString(res.getColumnIndex("contest_id")));
            ArrayList<Attachment> attachments=new ArrayList<Attachment>();

            long startAlt=  System.currentTimeMillis();
            ArrayList<Alternative> alternatives = alt.getAllAlternativesByQuestionId(question_id, dbAlt, dbAtt);
            long endAlt=  System.currentTimeMillis();
            sumAlt+=(endAlt-startAlt);

            long startAtt=  System.currentTimeMillis();
            if(hasAttachment) {
                attachments = att.getAllAttachmentsByLinkId(question_id, "question", dbAtt);
            }
            long endAtt=  System.currentTimeMillis();
            sumAtt+=(endAtt-startAtt);

            Question tmp= new Question(question_id,category,text,isFavorited,isStudied,contest_id,
                    alternatives,attachments,hasAttachment);
            questions.add(tmp);
            res.moveToNext();
        }
        Log.i("LLL altern time: ", ""+sumAlt);
        Log.i("LLL attach time: ", ""+sumAtt);

        res.close();
        dbAlt.close();
        dbAtt.close();
        dbQuest.close();

        return questions;
    }

    @Override
    public void setQuestionStudied(long questionId, boolean studied,SQLiteDatabase dbQuest) {
        int valore=0;
        if(studied) valore=1;
        // dbQuest.execSQL("UPDATE \""+CONTACTS_TABLE_NAME+"\" SET isStudied='"+valore+"' WHERE question_id='"+questionId+"'");
        ContentValues contentValues = new ContentValues();
        contentValues.put("isStudied ", valore);
        dbQuest.update(CONTACTS_TABLE_NAME, contentValues, "question_id="+questionId, null);

    }

    public SQLiteDatabase openConnection(){
        return this.getReadableDatabase();

    }

    public void closeConnection(SQLiteDatabase db){
        db.close();
    }

    @Override
    public SQLiteDatabase openWritableConnection(){
        return this.getWritableDatabase();
    }


    @Override
    public ArrayList<Question> getAllQuestionsByContestId(long contestid, SQLiteDatabase dbAlt,
                                                          SQLiteDatabase dbQuest,SQLiteDatabase dbAtt) {
        long stQuest=System.currentTimeMillis();
        ArrayList<Question> array_list = new ArrayList<Question>();
        Cursor res =  dbQuest.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\" where contest_id='"+contestid+"'", null );
        res.moveToFirst();
        AlternativeDAOImpl alt = new AlternativeDAOImpl(context);
        AttachmentDAOImpl att= new AttachmentDAOImpl(context);
        long sumAlt=0;
        long sumAtt=0;
        while(res.isAfterLast() == false) {

            long question_id = Long.parseLong(res.getString(res.getColumnIndex("question_id")));
            String category = res.getString(res.getColumnIndex("Category"));
            String text = res.getString(res.getColumnIndex("Text"));

            int isFavoritedint = Integer.parseInt(res.getString(res.getColumnIndex("isFavorited")));
            boolean isFavorited = false;
            if (isFavoritedint == 1) isFavorited = true;

            int isStudiedint = Integer.parseInt(res.getString(res.getColumnIndex("isStudied")));
            boolean isStudied = false;
            if (isStudiedint == 1) isStudied = true;

            boolean hasAttachment=false;
            int intHasAttachment=Integer.parseInt(res.getString(res.getColumnIndex("hasAttachment")));
            if(intHasAttachment==1) hasAttachment=true;

            long contest_id = Long.parseLong(res.getString(res.getColumnIndex("contest_id")));
            ArrayList<Attachment> attachments=new ArrayList<Attachment>();

            long st=System.currentTimeMillis();
            ArrayList<Alternative> alternatives = alt.getAllAlternativesByQuestionId(question_id, dbAlt,dbAtt);
            long end=System.currentTimeMillis();
            sumAlt=sumAlt+(end-st);


            st=System.currentTimeMillis();
            if(hasAttachment) {
                attachments = att.getAllAttachmentsByLinkId(question_id, "question", dbAtt);
                end = System.currentTimeMillis();
            }
            sumAtt=sumAtt+(end-st);

            Question tmp= new Question(question_id,category,text,isFavorited,isStudied,contest_id,
                    alternatives,attachments,hasAttachment);
            array_list.add(tmp);
            res.moveToNext();
        }
        res.close();
        long endQuest=System.currentTimeMillis();
        Log.i("###", "Tempo del get Alternative: " + sumAlt);
        Log.i("###","Tempo del get Attachments: "+sumAtt);
        Log.i("###", "Tempo question extra operation: "+(endQuest-stQuest-sumAlt-sumAtt));
        Log.i("###", "Tempo totale del getQuestionById: "+(endQuest-stQuest));
        return array_list;
    }

    @Override
    public Question getQuestionById(long questionId, SQLiteDatabase dbAtt) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\" where question_id='" + questionId + "'", null);
        res.moveToFirst();

        long question_id=Long.parseLong(res.getString(res.getColumnIndex("question_id")));
        String category=res.getString(res.getColumnIndex("Category"));
        String text=res.getString(res.getColumnIndex("Text"));

        int isFavoritedint= Integer.parseInt(res.getString(res.getColumnIndex("isFavorited")));
        boolean isFavorited=false;
        if(isFavoritedint==1) isFavorited=true;

        int isStudiedint= Integer.parseInt(res.getString(res.getColumnIndex("isStudied")));
        boolean isStudied=false;
        if(isStudiedint==1) isStudied=true;

        boolean hasAttachment=false;
        int intHasAttachment=Integer.parseInt(res.getString(res.getColumnIndex("hasAttachment")));
        if(intHasAttachment==1) hasAttachment=true;

        long contest_id=Long.parseLong(res.getString(res.getColumnIndex("contest_id")));

        AlternativeDAOImpl alt= new AlternativeDAOImpl(context);
        SQLiteDatabase dbALT=alt.openConnection();
        ArrayList<Alternative>alternatives= alt.getAllAlternativesByQuestionId(question_id,dbALT,dbAtt);
        alt.closeConnection(dbALT);

        AttachmentDAOImpl att= new AttachmentDAOImpl(context);
        ArrayList<Attachment> attachments= att.getAllAttachmentsByLinkId(question_id, "question",dbAtt);
        att.closeConnection(dbAtt);

        Question tmp= new Question(question_id,category,text,isFavorited,isStudied,contest_id,
                alternatives,attachments,hasAttachment);

        res.close();
        db.close();
        return tmp;
    }



    @Override
    public Question deleteQuestion(long questionId, SQLiteDatabase dbQuest) {

        Question result= getQuestionById(questionId,dbQuest);
        if(result!=null) {
            dbQuest.delete(CONTACTS_TABLE_NAME, "question_id='" + questionId + "'", null);
        }
        return result;

    }


    @Override
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> array_list = new ArrayList<Question>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\"", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){

            long question_id=Long.parseLong(res.getString(res.getColumnIndex("question_id")));
            String category=res.getString(res.getColumnIndex("Category"));
            String text=res.getString(res.getColumnIndex("Text"));

            int isFavoritedint= Integer.parseInt(res.getString(res.getColumnIndex("isFavorited")));
            boolean isFavorited=false;
            if(isFavoritedint==1) isFavorited=true;

            int isStudiedint= Integer.parseInt(res.getString(res.getColumnIndex("isStudied")));
            boolean isStudied=false;
            if(isStudiedint==1) isStudied=true;

            boolean hasAttachment=false;
            int intHasAttachment=Integer.parseInt(res.getString(res.getColumnIndex("hasAttachment")));
            if(intHasAttachment==1) hasAttachment=true;

            long contest_id=Long.parseLong(res.getString(res.getColumnIndex("contest_id")));

            AlternativeDAOImpl alt= new AlternativeDAOImpl(context);
            SQLiteDatabase dbALT=alt.openConnection();
            AttachmentDAOImpl att= new AttachmentDAOImpl(context);
            SQLiteDatabase dbAtt= att.openConnection();

            ArrayList<Alternative>alternatives= alt.getAllAlternativesByQuestionId(question_id, dbALT, dbAtt);
            alt.closeConnection(dbALT);
            ArrayList<Attachment> attachments= att.getAllAttachmentsByLinkId(question_id, "question",dbAtt);
            att.closeConnection(dbAtt);


            Question tmp= new Question(question_id,category,text,isFavorited,isStudied,contest_id, alternatives,
                    attachments,hasAttachment);
            array_list.add(tmp);
            res.moveToNext();
        }
        res.close();
        db.close();
        return array_list;
    }

    @Override
    public ArrayList<String> getAllCategoriesByContestId(long contest_id) {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\" where contest_id='"+contest_id+"' group by Category", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("Category")));
            res.moveToNext();
        }
        res.close();
        db.close();
        return array_list;
    }

    //SELECT NAME, count(*) as NUM FROM tbl GROUP BY NAME

    @Override
    public ArrayList<Integer> getPercentageStudiedByCategory(long contest_id) {

        ArrayList<Integer> array_list = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select *, count(*) from \""+CONTACTS_TABLE_NAME+"\" where isStudied=1 AND contest_id='"+contest_id+"' group by Category", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getCount());
            res.moveToNext();
        }
        res.close();
        db.close();

        for(Integer i: array_list){
            Log.i("####","percenutale per categoria: "+i);
        }

        return array_list;
    }


}