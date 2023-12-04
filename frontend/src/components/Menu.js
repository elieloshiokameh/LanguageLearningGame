import "../App.css";
import { Outlet, Link } from "react-router-dom";

function Menu() {
    return (
        <div>
            <nav className="menu">
                <h1>Group 10's Language Learning Game</h1>
                <div className="buttons">
                    <Link to="/login">Login</Link>
                </div>
            </nav>
            <Outlet />
        </div>
    );
}

export default Menu;