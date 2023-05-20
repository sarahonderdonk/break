import { Link } from "react-router-dom";

/**
Renders the TopBar component that contains navigation links to other pages.
Uses react-router-dom to manage routing and linking between pages.
@returns {JSX.Element} - A JSX element that represents the TopBar component
*/
export default function TopBar() {
    return(
        <div className="TopBar" role="navigation">
          <li role="button">
            <Link to="/tutorials" id="Tutorial-Button">Tutorials</Link>
          </li>
          <li role="button">
            <Link to="/trainer" id="Trainer-Button">Trainer</Link>
          </li>
        </div>
)}