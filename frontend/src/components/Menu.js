import "../App.css";
import { Outlet, Link } from "react-router-dom";

function Menu() {
    return (
        <div>
            <nav className="menu">
                <h1>Group 1's Language Learning Game</h1>
                <div className="buttons">
                    <a href="/oauth2/authorization/google">Login Google</a>
                    {/*<a href="/oauth2/authorization/github">Login Github</a>*/}
                </div>
            </nav>
            <Outlet />
        </div>
    );
}

export default Menu;