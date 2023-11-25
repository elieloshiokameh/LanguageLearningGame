import './App.css';
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Menu from './components/Menu';
import Login from './components/Login';
import Languageselect from './components/Languageselect';
import Game from './components/Game';
import Statistics from './components/Statistics';
import Nopage from './components/Nopage';

export default function App() {

  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/">
            <Route index element={<Menu />} />
            <Route path="login" element={<Login />} />
            <Route path="languageselect" element={<Languageselect />} />
            <Route path="game/:language1/:language2" element={<Game />} />
            <Route path="statistics/:seconds" element={<Statistics />} />
            <Route path="*" element={<Nopage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App />);
