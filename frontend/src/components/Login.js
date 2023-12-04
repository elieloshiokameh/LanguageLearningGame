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
            <nav className="menu">
                <div className="buttons">
                    <a href="http://localhost:8080/oauth2/authorization/google">Login</a>
                    <Link to="/">Menu</Link>
                </div>
            </nav>
            <Outlet />
        </div>
    );
}

export default Login;