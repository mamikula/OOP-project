
<h1 align="center"> OOP-project </h1>

<p align="center">Life simulation is a program written in Java that was a final project for my Object Oriented Programming Course at AGH UST.</p>
<br>
<h2> Life Simulation </h2>

I had to implement some Darwin Life's rules into random generated world with animals that want to just reproduce, eat and survive the next day.
Full project information can be seen [here](https://github.com/apohllo/obiektowe-lab/tree/master/proj1).
#
<h3>Starting Parameters</h3>

* map width - the width of both maps
* map height - the height of both maps
* start energy - the amount of energy that animals spawn with
* move energy - the amount of energy it costs to move
* plant energy - the amount of energy the plant gives to an animal
* jungle ratio - percentage of the map which is covered by jungle
* grass magic evolution - makes it possible to spawn 1 copy of each animal if the amount of animals falls to 5 on GrassField
* rectangular magic evolution - makes it possible to spawn 1 copy of each animal if the amount of animals falls to 5 on RectangularMap
* animals at start - the amount of animals spawned at start
#
<h3>Game Rules</h3>

* Animal can act only if its energy is above 0
* Animal can reproduce only if it has 50% or more energy (based on max capacity)
* Animal gets greener the more it eats
* Animal gets more blueish when it is close to death
* Maximum of 2 plants can spawn each era (one in jungle area, one outside jungle area)
* There can be more than 1 animal on each square
* If there are more than 1 animal on square on which plant has grown - the plant is evenly distributed between most healthy animals
* Only 1 pair of animals can reproduce per square per era
* And some more not mentioned [rules](https://github.com/apohllo/obiektowe-lab/tree/master/proj1)
#

<h3>The Final Result</h3>
<br>

![](https://github.com/mamikula/OOP-project/blob/master/screen/start.png)

![](https://github.com/mamikula/OOP-project/blob/master/screen/simulation.png)



    

