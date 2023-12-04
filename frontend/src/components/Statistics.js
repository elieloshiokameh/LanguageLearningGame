import "../App.css";
import { Outlet, Link } from "react-router-dom";

function Statistics() {

    return (
        <div>
           <nav className="menu">
                <h1>Statistics</h1>
                <div className="buttons">
                    <Link to="/">menu</Link>
                </div>
            </nav>
            <Outlet />
        </div>
    );
}

export default Statistics;