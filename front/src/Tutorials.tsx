import TopBar from "./components/TopBar";
import {useState} from 'react';
import TutorialSection from "./components/TutorialSection";
import { MovesList } from "../src/jsons/tutorial"
import Select from 'react-select';



/**
 * The Tutorial component displays a list of tutorial sections for different dance moves. It 
 * uses the MovesList data to generate a list of TutorialSection components, which show the 
 * video and description for each move. It also includes a Select component for filtering moves 
 * by difficulty level. The Tutorials page also contains the TopBar. 
 * @returns --> the html for the tutorial page 
 */
export default function Tutorial(){
  //Create the use state for the difficultyvalue variable that changes with the dropdown menu 
  const [difficultyValue, setDifficultyValue] = useState<{ value: string ; label: string } | null>(null);

  //The different options for the Dropdown menu and their values 
  const difficultyOptions = [
    { value: "1", label: "Easy" },
    { value: "2", label: "Medium" },
    { value: "3", label: "Hard" },
    { value: "4", label: "All" }
  ];

  //This is the sections array that we fill with tutorial sections in the for loop and push on to the page
  let sections = [];
  // Loop through all the moves
  for(let i = 0; i < MovesList.Moves.length; i++){
      // If the move's difficulty matches the chosen difficulty, render a TutorialSection for it
      if(MovesList.Moves[i].Level == difficultyValue?.value){
          sections.push(<TutorialSection video = {MovesList.Moves[i].Link} moveName= {MovesList.Moves[i].Name} mainText= {MovesList.Moves[i].Description}
              altText= {MovesList.Moves[i].Name}></TutorialSection>)
      }

      // If the difficulty has not been chosen or is "All", render all the moves
      if(difficultyValue?.label == "All" || difficultyValue  == null){
        sections.push(<TutorialSection video = {MovesList.Moves[i].Link} moveName= {MovesList.Moves[i].Name} mainText= {MovesList.Moves[i].Description}
          altText= {MovesList.Moves[i].Name}></TutorialSection>)
      }
  }

  return(
      <div className="Backdrop">
        <TopBar />
        <h1 role="heading">Tutorials</h1>
        <div data-testid="difficulty-select">
          <Select
                id="difficulty-select"
                options={difficultyOptions}
                value={difficultyValue}

                onChange={value => setDifficultyValue(value)}
              />
        </div>
        <div className = "Backdrop" role="main">
          {sections}
        </div>
      </div>
  )

}

