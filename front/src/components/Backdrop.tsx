import React, { useState, useEffect } from "react";
import SequenceMenu from "./SequenceMenu";
import Images from "./Images";
import movesData from "../jsons/movephotos";

// The Backdrop contains all the elements unique to the Trainer page

// Define a type for the move object
type Move = {
  name: string;
  imagePath: string;
};

// Define a type for the props
type Props = {};

// Define the Backdrop component
export default function Backdrop(props: Props) {
  const [moves, setMoves] = useState<Array<string>>([]);
  const [imagePaths, setImagePaths] = useState<Array<string>>([]);
  const [currentMoveIndex, setCurrentMoveIndex] = useState(0);
  const [intervalId, setIntervalId] = useState<NodeJS.Timeout | null>(null);
  const [selectedLength, setSelectedLength] = useState(8);

  // Use the useEffect hook to update the currentMoveIndex every 4 seconds
  useEffect(() => {
    const id = setInterval(() => {
      setCurrentMoveIndex((prevIndex) =>
        prevIndex >= moves.length - 1 ? 0 : prevIndex + 1
      );
    }, 4000);
  
    // Clean up the interval when the component returns
    return () => clearInterval(id);
  }, [moves]);

  // Stops the interval
  const stopLoop = () => {
    if (intervalId) {
      clearInterval(intervalId);
    }
  };

  // Fetch move data from an API endpoint
  async function fetchData() {
    try {
      const response = await fetch(`http://localhost:3230/generate?length=${selectedLength}`);
      const data = await response.json();
      const moves: Array<string> = [];
      const paths: Array<string> = [];
  
      // Extract the move names and image paths from the data and add them to the respective arrays
      data.data.forEach((move: Move) => {
        const moveName = move.name;
        const movePath = movesData().get(moveName);
        if (movePath) {
          moves.push(moveName);
          paths.push(movePath);
        }
      });
  
      setMoves(moves);
      setImagePaths(paths);
      setCurrentMoveIndex(0); // reset current move index
    } catch (error) {
      console.error("Failed to fetch move data", error);
    }
  }

  // Display a move sequence as soon as the page renders
  useEffect(() => {
    fetchData();
  }, []);

    // Handle changes to the selectedLength
  const handleLengthChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedLength(parseInt(event.target.value));
  };

  // Handle clicks on the "Generate New Sequence" button
  const handleGenerateNewClick = () => {
    fetchData();
  };

  // Render the Backdrop component
  return (
    <div>
      <nav className="Backdrop">
        <SequenceMenu
          moves={moves}
          fetchData={fetchData}
          currentMoveIndex={currentMoveIndex}
          stopLoop={stopLoop}
        />
        <Images
          filePath={currentMoveIndex < imagePaths.length ? imagePaths[currentMoveIndex] : ""}
          altText="Image"
          currentMoveName={moves[currentMoveIndex]}
        />
      </nav>
      <div className="sequence-dropdown-and-button">
        <label htmlFor="length-select">Select length: </label>
        <select role="select" id="length-select" value={selectedLength} onChange={handleLengthChange}>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8">8</option>
          <option value="9">9</option>
          <option value="10">10</option>
          <option value="11">11</option>
          <option value="12">12</option>
        </select>
        <button role="button" id="generate-button" onClick={handleGenerateNewClick}>Generate New Sequence</button>
      </div>
    </div>
  );
}