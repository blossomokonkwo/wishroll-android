package co.WishRoll;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.*;

public class RegisterRequest extends StringRequest {
    //makes request to register and get a reponse as a string

    private static final String REGISTER_REQUEST_URL = "http://wishroll-test.000webhostapp.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String email, String name, int age, String username, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("name", name);
        params.put("age", age + "");
        params.put("username", username);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
