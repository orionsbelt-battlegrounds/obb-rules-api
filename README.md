OBB Rules API [![Build Status](https://travis-ci.org/orionsbelt-battlegrounds/obb-rules-api.svg)](https://travis-ci.org/orionsbelt-battlegrounds/obb-rules-api) ![Uptime](https://www.statuscake.com/App/button/index.php?Track=gXC2Qbo9AK&Days=30&Design=5)
=============

HTTP/JSON api for [OBB Rules](https://github.com/orionsbelt-battlegrounds/obb-rules). This api acts mainly as a json proxy to the main logic. This is a _pure service_, it does not have any side effects, like persistence. You can call it with the game's data repeatedly and easily build tools or bots around it.

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
      "min-move-percentage":0.2
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
