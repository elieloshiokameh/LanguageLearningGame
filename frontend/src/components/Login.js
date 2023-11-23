import "../App.css";
import { Outlet, Link } from "react-router-dom";

function Login() {
    return (
        <div className="Login">
            <nav>
                <h1>Login</h1>
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