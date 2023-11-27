import "../App.css";
import { Outlet, Link } from "react-router-dom";


function Nopage() {
    return (
        <div>
            <div
                style=
                {{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    color: "red",
                }}>
                    <h1>
                        404 Error
                    </h1>
            </div>
            <div className="buttons">
                <Link to="/">menu</Link>
            </div>
        </div>
    );
}

export default Nopage;