import "./styles/App.css";
import {Link} from "react-router-dom";

/**
 * This is the landing class, which defines the landing page which is the first page that the user
 * sees and the one the website opens up to. This page has two buttons that link to the two other pages being 
 * the Tutorial page and the Trainer page
 * @returns --> This function returns the html for the landing page
 */

export default function Landing() {
  return (
      <div className="Landing-Page">
        <div className="Landing-Title" role="heading">
          ✨Breaking✨
        </div>
        <div className="Landing-Buttons">
          <Link to="tutorials" className="Tutorials-Button" role="button">
            Tutorials
          </Link>
          <Link to="trainer" className="Trainer-Button" role="button">
            Trainer
          </Link>
        </div>
      </div>
  );
}
