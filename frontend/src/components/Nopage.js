import "../App.css";
import { Outlet, Link } from "react-router-dom";


function Nopage() {
    return (
        <div>
            <div
                style=
                {{
                    display: "flex",
                    justifyContent: "center",
                    color: "red",
                }}>
                    <h1 style={{fontSize: "100px"}}>
                        404 Error
                    </h1>
            </div>
            <div className="buttons">
                <Link to="/">menu</Link>
            </div>
            <div>
                <h2
                    style={{
                        display: "flex",
                        justifyContent: "center",
                        color: "grey",
                    }}
                >
                    <div>
                            <p style={{display: "flex", justifyContent: "center",}}>Uh-Oh...</p>
                            <p>There seems to be a problem. Please return to menu.</p>
                    </div>
                </h2>
            </div>
        </div>
    );
}

export default Nopage;