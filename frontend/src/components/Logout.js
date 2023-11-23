import "../App.css";
import { GoogleLogout } from 'react-google-login'

const clientId = "temp2"

function Logout() {

    const onSuccess = () => {
        console.log("logout success.");
    }

    return (
        <GoogleLogout
            clientId = {clientId}
            buttonText={"Logout"}
            onLogoutSuccess={onSuccess}
        />
    );
}

export default Logout;