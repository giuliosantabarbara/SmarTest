package jumapp.com.smartest;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;

/**
 * Created by marco on 12/04/2017.
 */
public class QuestionsHashMap {

    private ArrayList<Node> nodes= new ArrayList<>();

    public ArrayList<Question> getQuestionsByCategory(String category){
        for(int i=0; i<nodes.size();i++)
            if(nodes.get(i).getCategory().equalsIgnoreCase(category))
                return nodes.get(i).getQuestions();
        return null;
    }


    public void add(String category, Question question){
        boolean trovato=false;
        int i=0;
        while(trovato==false && i<nodes.size()){
            if(nodes.get(i).getCategory().equalsIgnoreCase(category)){
                trovato=true;
                nodes.get(i).getQuestions().add(question);
            }
            i++;
        }
        if(trovato==false) nodes.add(new Node(category,question));
    }

    public int getSize(){
        int total=0;
        for(Node n: nodes)total+= n.getQuestions().size();
        return total;
    }


    public class Node {

        private ArrayList<Question> questions= new ArrayList<>();
        private String category;

        public Node(String category, ArrayList<Question> questions){
            this.category=category;
            this.questions=questions;

        }

        public Node(String category, Question question){
            this.category=category;
            ArrayList<Question> quests= new ArrayList<>();
            quests.add(question);
            this.questions=quests;

        }

        public ArrayList<Question> getQuestions() {
            return questions;
        }

        public void setQuestions(ArrayList<Question> questions) {
            this.questions = questions;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}
