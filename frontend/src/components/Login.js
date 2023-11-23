import "../App.css";
import { Outlet, Link } from "react-router-dom";

function Login() {
    return (
        <div>
            <nav>
                <h1>Login</h1>
                <button><Link to="/languageselect">continue</Link></button>
                <button><Link to="/">menu</Link></button>
            </nav>
            <Outlet />
        </div>
    );
}

export default Login;