export interface MockJson {
  result: string;
  data: any;
}

/**
 * This file mocks JSON responses from the server. One response is for a 4-move sequence and one is for
 * an 8-move sequence.
 */
export function getMockJson(length: number): MockJson {
  if (length == 4) {
    return (
        {
          "result": "success",
          "data": [
              { "difficulty": 1, "id": 7, "links": [ 1, 0, 17 ], "name": "Sidestep", "type": "Toprock" },
              { "difficulty": 2, "id": 20, "links": [ -1 ], "name": "Coffee Grinder (Get Down)", "type": "Go-Down" },
              { "difficulty": 1, "id": 4, "links": [ 2, 3, 4, 6, 8, 9, 10, 11, 12, 14 ], "name": "CC", "type": "Footwork" },
              { "difficulty": 2, "id": 12, "links": [ 5 ], "name": "Chair Freeze", "type": "Freeze" } ]
        }
    )
  } else {
    return (
        {
          "result": "success",
          "data": [
            {"difficulty": 1, "id": 7, "links": [1, 0, 17], "name": "Sidestep", "type": "Toprock"},
            {"difficulty": 1, "id": 1, "links": [0, 7, 17], "name": "Hip Twist", "type": "Toprock"},
            {
              "difficulty": 2,
              "id": 20,
              "links": [-1],
              "name": "Coffee Grinder (Get Down)",
              "type": "Go-Down"
            },
            {
              "difficulty": 1,
              "id": 9,
              "links": [5, 12, 6, 15, 11],
              "name": "Coffee Grinder (Floor)",
              "type": "Footwork"
            },
            {
              "difficulty": 2,
              "id": 11,
              "links": [2, 3, 4, 8, 9, 10, 11, 5, 12],
              "name": "Hooks",
              "type": "Footwork"
            },
            {
              "difficulty": 1,
              "id": 10,
              "links": [2, 3, 4, 8, 9, 10, 11, 14, 17],
              "name": "Kickout",
              "type": "Footwork"
            },
            {
              "difficulty": 1,
              "id": 9,
              "links": [5, 12, 6, 15, 11],
              "name": "Coffee Grinder (Floor)",
              "type": "Footwork"
            },
            {"difficulty": 2, "id": 5, "links": [12, 13], "name": "Baby Freeze", "type": "Freeze"}
          ]
        }
    )
  }
}
