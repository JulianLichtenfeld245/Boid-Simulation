# Boids
A 3D Boid Simulation based on Craig Reynold's Boid characteristics

#Classes
Boid: Vector position, Vector velocity
methods change above values based on boids array from nearbyBoids
set (set or list or nearby boids not including self boid)
    methods: separationRule, alignmentRule, cohesionRule, proximityBoids
Each boid represented by a jpeg, has center position, use StdDraw.picture
Flock: a group of boids, boids only identify nearby Boids in the same flock
    methods: add(boid)
Vector: x,y; can be position or change in position between frames
    methods: add(vectorA, vectorB), subtract(vectorA, vectorB)
Main: arrayList of Boids,
    check proximity, return boids array
Renderer:
    Methods: initialize(ScreenWidth, ScreenHeight, initialBoidsArray), render(currentBoidsArray)


#Sources
Precreated psuedocode: http://www.vergenet.net/~conrad/boids/pseudocode.html
https://cs.stanford.edu/people/eroberts/courses/soco/projects/2008-09/modeling-natural-systems/boids.html
https://www.red3d.com/cwr/boids/


#Next Steps
- interactive switches/levers for different rates, ability to do different size screens/landscapes
- mouse as center of flock or click to add new boids
- different flocks (distinguished by different colors)
- have flocks take specific shapes (sphere, disc, etc.)