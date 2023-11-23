import "../App.css";
import { Outlet, Link } from "react-router-dom";

function Menu() {
    return (
        <div>
            <nav>
                <h1>Menu</h1>
                <div className="buttons">
                    <Link to="/login">Login</Link>
                </div>
            </nav>
            <Outlet />
        </div>
    );
}

export default Menu;