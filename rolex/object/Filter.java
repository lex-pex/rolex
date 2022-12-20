package rolex.object;

// mock
public class Filter {

    private String expression;

    public Filter() {
        super();
    }

    public Filter(String expression) {
        this.expression = expression;
    }

    public static Filter eq(String name, String applicationName) {
        return null;
    }

    public static Filter compile(String expression) {
        return new Filter("expression");
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
