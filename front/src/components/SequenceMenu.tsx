import {useEffect } from "react";

/**

Props for the SequenceMenu component.
  @param {string[]} moves - The list of moves in the sequence.
  @param {number} currentMoveIndex - The index of the current move in the sequence.
  @param {Function} stopLoop - A function to stop the loop.
  @param {Function} fetchData - A function to fetch new data.
*/
type Props = {
  moves: string[];
  currentMoveIndex: number;
  stopLoop: () => void; // New prop to stop the loop
  fetchData: () => void;
};

/**

A component that displays the current sequence of moves.
@param {Props} props - The props for the component.
@returns {JSX.Element} - The rendered component.
*/
export default function SequenceMenu({
  moves,
  currentMoveIndex,
  stopLoop,
    fetchData
}: Props) {

  useEffect(() => {
    // Check if the current move index is the last move
    if (currentMoveIndex === moves.length - 1) {
      // Call the stopLoop function to stop the loop
      stopLoop();
    }
  }, [currentMoveIndex, moves, stopLoop]);

  return (
      <div role="list">
        <div className="sequence-menu">
          <h2 className="sequence-menu-header">Current Sequence</h2>
          <div className="sequence-menu-moves">
            {moves.map((move, index) => (
                <div
                    role="listitem"
                    key={index}
                    className={`sequence-menu-move ${
                        index === currentMoveIndex ? "highlighted" : ""
                    }`}
                >
                  {move}
                </div>
            ))}
          </div>
        </div>
      </div>
  );
}