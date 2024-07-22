import me.imsergioh.pluginsapi.util.GradientUtil;
import me.imsergioh.pluginsapi.variables.EasyGradientVariable;
import me.imsergioh.pluginsapi.variables.MiniMessageHexVariable;

public class TestMain {


    public static void main(String[] args) {
        MiniMessageHexVariable variable = new MiniMessageHexVariable();
        System.out.println( variable.parse("<#54ff79>➤ Rango: <rank>"));
    }

}
