# Boids
We gonna make some boids


#Classes
JULIAN Boid: Vector position, Vector velocity
methods change above values based on boids array from check proximity
set (set or list or nearby boids not including self boid)
    methods: separationRule, alignmentRule, cohesionRule, proximityBoids
Each boid represented by a jpeg, has center position, use StdDraw.picutre
JULIAN Vector: x,y; can be position or change in position between frames
    methods: add(vectorA, vectorB), subtract(vectorA, vectorB)
Main: arrayList of Boids,
    check proximity, return boids array
AUDREY Renderer:
    Methods: initialize(ScreenWidth, ScreenHeight, initialBoidsArray), render(currentBoidsArray)
Test: place to test all our classes and end product


#Sources (aka soyces)
Precreated psuedocode: http://www.vergenet.net/~conrad/boids/pseudocode.html
https://cs.stanford.edu/people/eroberts/courses/soco/projects/2008-09/modeling-natural-systems/boids.html
https://www.red3d.com/cwr/boids/