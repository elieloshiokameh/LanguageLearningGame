import "../App.css";
import { Outlet, Link } from "react-router-dom";

function Statistics() {

    return (
        <div>
           <nav>
                <h1>Stats</h1>
                <Link to="/">menu</Link>
            </nav>
            <Outlet />
        </div>
    );
}

export default Statistics;