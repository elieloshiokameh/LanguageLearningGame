import "../App.css";
import { GoogleLogin} from 'react-google-login'
import { Outlet, Link } from "react-router-dom";
import Logout from "./Logout";

const clientId = "temp"

function Login() {

    const onSuccess = (res) => {
        console.log("login success. res:", res.profileObj);
    }

    const onFailure = (res) => {
        console.log("login fail. res:", res);
    }

    return (
        <div>
            <nav>
                <div id="signInButton">
                    <GoogleLogin
                        clientId = {clientId}
                        buttonText="Login"
                        onSuccess={onSuccess}
                        onFailure={onFailure}
                        cookiePolicy={'single_host_origin'}
                        isSignedIn={true}
                    />
                    <Logout />

                </div>
                <div className="buttons">
                    <Link to="/languageselect">continue</Link>
                    <Link to="/">menu</Link>
                </div>
            </nav>
            <Outlet />
        </div>
    );
}

export default Login;