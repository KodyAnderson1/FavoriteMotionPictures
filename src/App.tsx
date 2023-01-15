import { useState, useEffect } from "react";
import { Container, Row, Card } from "react-bootstrap";
import Col from "react-bootstrap/esm/Col";
import "./App.css";

/**
 * Have a Show more Button that shows a description
 * Add (or remove from favorites)
 *
 * Have a button on hover to add OR remove from favorites
 */
type jsonResult = {
  showName: String;
  hasEnded: Boolean;
  genres: [];
  id: Number;
  summary: String;
  url: String;
  image: {
    medium: String;
    original: String;
  };
  rating: Number;
  score: Number;
};

function App() {
  const [searchItem, setSearchItem] = useState<String>("");
  const [searchResults, setSearchResults] = useState<any>();

  function jsonToObject(results: []): any {
    const res = results.map((element: any) => {
      // console.log(element);
      return {
        showName: element.show.name,
        hasEnded: element.show.ended === "Ended" ? true : false,
        genres: element.show.genres,
        id: element.show.id,
        summary: element.show.summary,
        url: element.show.url,
        image: {
          medium: element.show.image.medium,
          original: element.show.image.original,
        },
        rating: element.show.rating.average,
        score: element.score,
      };
    });
    setSearchResults(res);
    // console.log("REEEE\n", res);
  }

  function handleOnSubmit(event: React.FormEvent): void {
    event.preventDefault();
    fetch(`https://api.tvmaze.com/search/shows?q=${searchItem}`)
      .then((res) => res.json())
      .then((results) => jsonToObject(results));
    // console.log(searchResults);
  }
  return (
    <div className="App">
      <Container className="mt-5">
        <Row>
          <h1 className="d-flex justify-content-center">Search for Movies / TV Shows!</h1>
          <form className="input" onSubmit={handleOnSubmit}>
            <input
              onChange={(e) => setSearchItem(e.target.value)}
              className="input-box"
              type="search"
            />
            <button className="input-submit">Go</button>
          </form>
        </Row>
        <Row>
          {searchResults?.map((result: jsonResult) => {
            return <SingleMovieCard result={result} />;
          })}
        </Row>
      </Container>
    </div>
  );
}

function SingleMovieCard(props: any) {
  const result = props.result;
  console.log(props.result);
  return (
    <Col xs={3} style={{ margin: "2rem" }}>
      <Card style={{ width: "18rem" }}>
        <Card.Img
          variant="top"
          style={{ width: "18rem", height: "18rem" }}
          src={`${result.image.medium}`}
        />
        <Card.Body>
          <Card.Title>{result.showName}</Card.Title>
          <Card.Text>{result.summary}</Card.Text>
        </Card.Body>
      </Card>
    </Col>
  );
}

export default App;
