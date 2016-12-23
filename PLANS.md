##  Doing ScalaJS implementation
### Architecture outline

-  domain model
- `ochrons/diode` for events
- `single html page` with vending-machine-like layout
-  connecting those things together

### Work plan
- implement domain model (.5h)                
- do basic diode routing\actions (1h)
- setup scala.js compiler, do first run (.5h) `risky` 
- html page layout (scala tags?)  (1h) 
  - nothing fancy, start with simple buttons for 
    - goods list, to pick some
    - insert coin slot
    - get candies slot
    - money pocket?  (`earn` coins somehow? or just increment from timer)
- wire things together, smoke testing (1h)   
- clean things (.5)
- gh-pages shipping (.5-1h) `risky`
- final thoughts, further plans (1h) 
- some unit tests if enough time

*`risky` haven't done that, could take longer

estimated time ~6h


####What is left to do
1. deploy to gh and test gh-pages
2. polish ui / minor logic updates