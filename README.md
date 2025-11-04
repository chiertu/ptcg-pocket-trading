## Trading Rule
### Initiating a trade
- A wants Card1
- A sees a list of potential trading partners who:
  - want to give out Card1 
  - want at least 1 of the card that A wants to give out 
- A sees no trading partner if:
  - A does not list any card to give out
  - no player meets the criteria
- For each potential trading partner, A sees a list of cards that:
  - that trading partner wants
  - A wants to give out
- A select one trading partner (B) and one card to give out to that player (Card2)
- A sends out a trading request to B
- B sees the trade offer with the following info:
  - give out Card1
  - receive Card2
  - GameID of A
### Accepting a trade
- B accepts the trade and sends back GameID
### Denying a trade
- B denies the trade
### Closing a trade - successful
- A marks the trade successful
- system marks Card1 obtained
- system marks Card2 unavailable
- other related trades automatically `terminated`
  - trade that require Card1 being absent
  - trade that require Card2 being available
### Closing a trade - failed
- A marks the trade failed

## Trade Status
- `waiting decision`
- `denied`
- `terminated`
- `confirmed`
- `successful`
- `failed`

## Q&A
- Can one player has multiple ongoing trade requests?
  - Yes

## What makes the platform good 
- online indicator - players can make immediate trades
- multiple ongoing trades
- 2-way confirmation instead of 3-way
- collection focused (language, artist)


## Image API to consider
- https://pokemontcg.io/
- https://tcgdex.dev/
- https://drive.google.com/drive/folders/1_2YURmd7dYnCX3VuDLQobLNSJbAeavpt



## TODO
- currently the scrapper save images with spaces in them, and the process to remove the spaces is done mannually

## Contributor


