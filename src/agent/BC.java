package agent;

import java.util.ArrayList;

public class BC {

    private ArrayList<Rule> knowledgeBase;

    public BC() {
        this.knowledgeBase = new ArrayList<>();
    }

    public void addRule(Rule rule) {
        this.knowledgeBase.add(rule);
    }

    public Rule getRule(boolean s1, boolean s2, boolean s3, boolean s4, boolean s5, boolean s6, boolean s7,
            boolean s8) {
        for (Rule rule : this.knowledgeBase) {
            if (rule.matches(s1, s2, s3, s4, s5, s6, s7, s8)) {
                return rule;
            }
        }
        // Default rule
        return this.knowledgeBase.get(this.knowledgeBase.size() - 1);
    }

    public void clear() {
        this.knowledgeBase.clear();
    }

    @Override
    public String toString() {
        return "BC [knowledgeBase=" + knowledgeBase + "]";
    }

}
