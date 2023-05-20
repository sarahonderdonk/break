// The Images component represents the image on the Trainer screen

// Define the properties of the Images component
interface ImagesProps {
  filePath: string;
  altText: string;
  currentMoveName: string;
}

// Defines the Images component with the properties defined in the ImagesProps interface
export default function Images({ filePath, altText, currentMoveName }: ImagesProps) {
  return (
    <div className="ImagesContainer" role="main">
      <div className="Images">
        <img src={filePath} alt={altText} />
      </div>
      <div className="CurrentMove">{`Current Move: ${currentMoveName}`}</div>
    </div>
  );
}