import "../App.css";
import Flag from 'react-world-flags';
import { Outlet, Link } from "react-router-dom";

function Languageselect() {

    return (
        <div>
            <nav>
                <h1>Language Select</h1>
                <button><Link to="/game">continue</Link></button>
            </nav>
            <Outlet />
        </div>
    );
}

export default Languageselect;