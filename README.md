# Whats_That_Plant

## Important Notes
	To be able to use the api, a "credit" has to be available. 
	
	In the admin page you can assign credits to the dev account.
	We have 100 per month. I've just assigning as many as I need per time I use it.

## API:
	plant.id

## Documentation:
	https://github.com/flowerchecker/Plant-id-API/wiki
	
## Admin Page:
	https://admin.mlapi.ai/
	
## Dev API Key:
	
	
	
	
## Response Example:	[returned from app] 
	`PlantData(
		id=75731085, 
		suggestions=[
			Suggestion(
				id=421596931, 
				plantName=Taraxacum officinale, 
				plantDetails=PlantDetails(
					commonNames=[common dandelion], 
					edibleParts=[flowers, leaves], 
					propagationMethods=[division, seeds], 
					taxonomy=Taxonomy(
						className=Magnoliopsida, 
						family=Asteraceae, 
						genus=Taraxacum, 
						kingdom=Plantae, 
						order=Asterales, 
						phylum=Magnoliophyta), 
					url=https://en.wikipedia.org/wiki/Taraxacum_officinale, 
					scientificName=Taraxacum officinale
				), 
				probability=0.9173180366038366
			), 
			Suggestion(
				id=421596932, 
				plantName=Taraxacum, 
				plantDetails=PlantDetails(
					commonNames=[dandelions], 
					edibleParts=null, 
					propagationMethods=null, 
					taxonomy=Taxonomy(
						className=Magnoliopsida, 
						family=Asteraceae, 
						genus=Taraxacum, 
						kingdom=Plantae, 
						order=Asterales, 
						phylum=Magnoliophyta
					), 
					url=https://en.wikipedia.org/wiki/Taraxacum, 
					scientificName=Taraxacum
				), 
				probability=0.03277610971404955
			), 
			Suggestion(
				id=421596933, 
				plantName=Taraxacum erythrospermum, 
				plantDetails=PlantDetails(
					commonNames=[red-seeded dandelion], 
					edibleParts=[flowers, leaves], 
					propagationMethods=null, 
					taxonomy=Taxonomy(
						className=Magnoliopsida, 
						family=Asteraceae, 
						genus=Taraxacum, 
						kingdom=Plantae, 
						order=Asterales, 
						phylum=Magnoliophyta
					), 
					url=https://en.wikipedia.org/wiki/Taraxacum_erythrospermum, 
					scientificName=Taraxacum erythrospermum
				), 
				probability=0.012416032542953736
			)], 
			isPlantProbability=0.9974805354, 
			isPlant=true
		)`


