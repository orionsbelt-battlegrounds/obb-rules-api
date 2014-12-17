OBB Rules API
=============
[![Gitter](https://badges.gitter.im/Join Chat.svg)](https://gitter.im/orionsbelt-battlegrounds/open-source?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Build Status](https://travis-ci.org/orionsbelt-battlegrounds/obb-rules-api.svg)](https://travis-ci.org/orionsbelt-battlegrounds/obb-rules-api) [![Coverage Status](https://coveralls.io/repos/orionsbelt-battlegrounds/obb-rules-api/badge.png)](https://coveralls.io/r/orionsbelt-battlegrounds/obb-rules-api) [![Dependency Status](https://www.versioneye.com/user/projects/545250c530a8fe529600003e/badge.svg?style=flat)](https://www.versioneye.com/user/projects/545250c530a8fe529600003e)  ![Uptime](https://www.statuscake.com/App/button/index.php?Track=gXC2Qbo9AK&Days=30&Design=5)

HTTP/JSON api for [OBB Rules](https://github.com/orionsbelt-battlegrounds/obb-rules). This api acts mainly as a json proxy to the main logic. This is a _pure service_, it does not have any side effects, like persistence. You can call it with the game's data repeatedly and easily build tools or bots around it.

Also check out the game's main API hub: [OBB API](https://github.com/orionsbelt-battlegrounds/obb-api).

![game image](https://camo.githubusercontent.com/f5fc5f992b37d31fb9b4aeb2d0d2241698779606/68747470733a2f2f7261772e6769746875622e636f6d2f6f72696f6e7362656c742d626174746c6567726f756e64732f626174746c652d656e67696e652d61692f6d61737465722f646f632f53616d706c65426f6172642e6a706567)

## HTTP API

Available at [rules.api.orionsbelt.eu](http://rules.api.orionsbelt.eu).

### `GET /` api version and generic information

You can verify the api's version and several _constants_ used on the game's logic.

```
> curl http://rules.api.orionsbelt.eu/
```
```javascript
{  
   "name":"obb-rules-api",
   "version":"1.0.0-SNAPSHOT",
   "rules":{  
      "version":"1.0.0-SNAPSHOT",
      "max-action-points":6,
      "min-move-percentage":0.2,
      "default-board-width":8,
      "default-board-height":8
   }
}
```

### `GET /game/random` creates a random game

It will create a game in the _deploy_ state, where each player has the same unit _stash_ and the _stash_ has randomly selected 8 different units.

```
> curl http://rules.api.orionsbelt.eu/game/random
```
```javascript
{  
   "state":"deploy",
   "stash":{  
      "p2":{  
         "toxic":100,
         "heavy-seeker":25,
         "nova":25,
         "kamikaze":50,
         "rain":100,
         "scarab":50,
         "worm":50,
         "crusader":25
      },
      "p1":{  
         "toxic":100,
         "heavy-seeker":25,
         "nova":25,
         "kamikaze":50,
         "rain":100,
         "scarab":50,
         "worm":50,
         "crusader":25
      }
   },
   "width":8,
   "height":8,
   "elements":{}
}
```

### `GET /game/turn/:player?context=:data` processes a turn

It will request a process turn by the given `:player` (`p1` or `p2`). The data will be a JSON message consisting of the current state of the game, and a collection of actions to apply to that game. The result will yield the next game state if successfull, or a list of errors.

`:data` is JSON payload, that can be given via a GET request in the query string via `context` (url encoded) or in a POST request's body.

Expected data:

name | default | description
--- | --- | --- | ---
**game** | - | the current board
**actions** | - | the list of actions to process on this turn
**viewed-by** | p1 | the given board is viewed from this player
**action-focus** | p1 | the received actions were performed with this focus (p1 or p2)
**p2-focused-board** | false | if the server should also return a view of the board from p2

```
> curl http://rules.api.orionsbelt.eu/game/turn/p1?context=%7B%22game%22%3A%7B%22state%22%3A%22p1%22,%22elements%22%3A%7B%22%5B1%201%5D%22%3A%7B%22player%22%3A%22p1%22,%22unit%22%3A%22kamikaze%22,%22quantity%22%3A1,%22coordinate%22%3A%5B1,1%5D,%22direction%22%3A%22south%22%7D,%22%5B1%204%5D%22%3A%7B%22player%22%3A%22p2%22,%22unit%22%3A%22rain%22,%22quantity%22%3A1,%22coordinate%22%3A%5B1,4%5D,%22direction%22%3A%22east%22%7D%7D%7D,%22actions%22%3A%5B%5B%22move%22,%5B1,1%5D,%5B1,2%5D,1%5D,%5B%22move%22,%5B1,2%5D,%5B1,3%5D,1%5D,%5B%22attack%22,%5B1,3%5D,%5B1,4%5D%5D%5D%7D
```

POST example:

```javascript
{
  "game": {
    "state": "p1",
    "elements": {
      "[1 1]": {
        "player": "p1",
        "unit": "kamikaze",
        "quantity": 1,
        "coordinate": [1,1],
        "direction": "south"
      },
      "[1 4]": {
        "player": "p2",
        "unit": "rain",
        "quantity": 1,
        "coordinate": [1,4],
        "direction": "east"
      }
    }
  },
  "action-focus":"p1",
  "actions": [
    ["move", [1,1], [1,2], 1],
    ["move", [1,2], [1,3], 1],
    ["attack", [1,3], [1,4]]
  ],
  "p2-focused-board":false
}
```

Example response:

```javascript
{
  "success": true,
  "board": {
    "action-results": [
      [
        ["move", [1,1], [1,2], 1],
        {
          "success": true,
          "cost": 1,
          "message": "OK"
        }
      ],
      [
        ["move", [1,2], [1,3], 1],
        {
          "success": true,
          "cost": 1,
          "message": "OK"
        }
      ],
      [
        ["attack", [1,3], [1,4]],
        {
          "success": true,
          "cost": 1,
          "message": "OK"
        }
      ]
    ],
    "state": "final",
    "elements": {
      "[1 3]": {
        "frozen": true,
        "player": "p1",
        "unit": "kamikaze",
        "quantity": 1,
        "coordinate": [1,3],
        "direction": "south"
      }
    }
  },
  "cost": 3,
  "message": "TurnOK"
}
```

### `GET /game/focus/:player?context=:data` view the board as `player`

Get a current board/game in another player focus. For example, if by default player A is at the _bottom_,
this request will show the board as if player A was at the _top_. The input is the same as in `/game/turn`.

```
http://rules.api.orionsbelt.eu/game/focus/p2?context={%22game%22:{%22state%22:%22p1%22,%22elements%22:{%22[1%201]%22:{%22player%22:%22p1%22,%22unit%22:%22kamikaze%22,%22quantity%22:1,%22coordinate%22:[1,1],%22direction%22:%22south%22},%22[1%204]%22:{%22player%22:%22p2%22,%22unit%22:%22rain%22,%22quantity%22:1,%22coordinate%22:[1,4],%22direction%22:%22east%22}}},%22actions%22:[[%22move%22,[1,1],[1,2],1],[%22move%22,[1,2],[1,3],1],[%22attack%22,[1,3],[1,4]]]}
```

```javascript
{
  "success": true,
  "board": {
    "state": "p1",
    "elements": {
      "[8 8]": {
        "player": "p1",
        "unit": "kamikaze",
        "quantity": 1,
        "coordinate": [
          8,
          8
        ],
        "direction": "north"
      },
      "[8 5]": {
        "player": "p2",
        "unit": "rain",
        "quantity": 1,
        "coordinate": [
          8,
          5
        ],
        "direction": "west"
      }
    }
  },
  "cost": "OK",
  "message": "OK"
}
```

### `GET /units/:unit` fetch the info of the given `:unit`

You can request the unit given it's `name` or `code`.

```
> curl http://rules.api.orionsbelt.eu/units/crusader
```
```javascript
{  
   "category":"heavy",
   "displacement":"air",
   "name":"crusader",
   "value":62,
   "movement-cost":4,
   "type":"mechanic",
   "defense":2200,
   "code":"c",
   "movement-type":"front",
   "attack":2400,
   "range":6
}
```

### `GET /units` fetch all the available combat units

```
> curl http://rules.api.orionsbelt.eu/units
```
```javascript
[  
   {  
      "category":"light",
      "displacement":"air",
      "name":"rain",
      "value":4,
      "movement-cost":1,
      "type":"mechanic",
      "defense":70,
      "code":"r",
      "movement-type":"all",
      "attack":120,
      "range":1
   },
   // ... and so on
]
```

### `GET /ranking/default` shows the default ranking

Shows the default ranking information for new players

```
> curl http://rules.api.orionsbelt.eu/ranking/default
```
```javascript
{  
   "rating":1500.0,
   "rd":350.0,
   "volatility":0.06
}
```

### `GET /ranking/calculate?winner=:d1&other=:d2` calculate new rankings

Calculates the new ranking given the winner's current ranking and the other player's ranking.

```
http://rules.api.orionsbelt.eu/ranking/calculate?winner=%7B%22rating%22%3A1500.0%2C%22rd%22%3A350.0%2C%22volatility%22%3A0.06%7D&other=%7B%22rating%22%3A1500.0%2C%22rd%22%3A350.0%2C%22volatility%22%3A0.06%7D
```
```javascript
{  
   "winner":{  
      "rating":1662.3108925690603,
      "rd":290.31896252204893,
      "volatility":0.05999926959652241
   },
   "other":{  
      "rating":1337.6891074309397,
      "rd":290.31896252204893,
      "volatility":0.05999926959652241
   }
}
```
