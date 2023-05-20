
import TopBar from "./components/TopBar";
import Backdrop from "./components/Backdrop";

/**
 * This class defines the Trainer page which is where users are able to generate and view move sequences.
 * This page is made up of the the TopBar, Backdrop, and a heading.
 * @returns --> The html for the Trainer page 
 */
function Trainer() {
    return (
        <div>
          <TopBar/>
          <h1 id="Trainer-Header" role="heading">Trainer</h1>
          <Backdrop />
        </div>
    );
  }
  
  export default Trainer;
  