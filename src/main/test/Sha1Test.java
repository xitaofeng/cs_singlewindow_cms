import com.jspxcms.common.security.HashCredentialsDigest;
import com.jspxcms.common.security.SHA1CredentialsDigest;
import org.apache.shiro.crypto.hash.Sha1Hash;

public class Sha1Test {

    public static void main(String[] args) {
        /*String s = new Sha1Hash("Dzka8888", "admin").toString();
        String s2 = new Sha1Hash("Dzka8888").toString();
        System.out.println(s);
        System.out.println(s2);*/
        //系统用的这个加密用户密码
        SHA1CredentialsDigest sha1CredentialsDigest = new SHA1CredentialsDigest();
        String s3 = sha1CredentialsDigest.digest("Dzka8888", null);
        System.out.println(s3);

    }

}
