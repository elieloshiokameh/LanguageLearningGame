import "../App.css";
import { Outlet, Link } from "react-router-dom";

function Menu() {
    return (
        <div>
            <nav>
                <h1>Menu</h1>
                <Link to="/login">Login</Link>
            </nav>
            <Outlet />
        </div>
    );
}

export default Menu;