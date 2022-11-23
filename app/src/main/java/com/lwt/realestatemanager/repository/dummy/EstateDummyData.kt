@file:Suppress("SpellCheckingInspection")

package com.lwt.realestatemanager.repository.dummy

import com.lwt.realestatemanager.model.Estate
import com.lwt.realestatemanager.model.Photo
import java.util.*
import kotlin.random.Random.Default.nextInt


object EstateDummyData {

	// ---------------------
	// Random Model
	// ---------------------
	fun randomLoremIpsum(): String {
		return listOf(
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pulvinar erat eget auctor ultricies. Vestibulum id purus iaculis, semper mauris id, mollis velit. Maecenas in tempor metus. Sed sed lectus tellus. Duis condimentum odio arcu, nec sodales nisl feugiat at. Nulla ut nisi eu lorem pulvinar efficitur. Nunc risus felis, fringilla et tempor is, convallis quis dolor. Mauris varius mattis imperdiet. Quisque ullamcorper erat ut dui tempus gravida. Maecenas laoreet et quam vel fringilla. Quisque sed libero varius, auctor augue non, viverra mi. Praesent cursus enim eu mauris suscipit ornare. In pulvinar nulla finibus ante ultrices, at rhoncus nulla tristique. Ut at sapien ac massa iaculis pharetra non quis metus. Morbi quis ullamcorper diam, sit amet blandit velit.",
			"Mauris euismod rutrum mauris, vitae tincidunt turpis fringilla vel. Praesent vehicula urna sed ligula dignissim, vitae rhoncus augue imperdiet. Etiam ac sem justo. Quisque condimentum tincidunt urna, non bibendum nisl. Donec sollicitudin tortor eget nisl ultrices, non mattis tellus varius. Curabitur ultricies lorem et nibh venenatis laoreet. Etiam orci tellus, tincidunt et iaculis sit amet, scelerisque ut velit. Donec et malesuada lectus. Maecenas elit lectus, consequat ultricies dictum aliquet, rutrum a tortor. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin ornare ullamcorper volutpat. Vestibulum ligula augue, commodo ut condimentum nec, bibendum varius mauris.",
			"Pellentesque semper at eros convallis lobortis. Etiam quam mi, eleifend eget finibus eu, auctor vel metus. Aliquam leo enim, vulputate in justo at, condimentum viverra velit. Aenean ex turpis, laoreet ac mi ut, consectetur facilisis ligula. Morbi a maximus lorem. Curabitur vitae enim ullamcorper, fringilla tellus eu, tristique libero. Sed lectus mauris, pulvinar vitae interdum quis, bibendum ut urna. Donec dolor turpis, consequat eget tortor elementum, elementum blandit risus. Morbi blandit dolor vel lectus eleifend, ut auctor massa vehicula. Morbi aliquam arcu augue, sed mollis enim feugiat id. Aliquam vitae felis at elit laoreet cursus sit amet id nunc. Nam eget ex id magna elementum ultricies. Praesent tempus purus a lectus aliquam ultrices. Maecenas id felis fermentum, vestibulum sem eget, vulputate ipsum.",
			"Cras sit amet elementum neque. Nunc condimentum ex elit, ac posuere nulla pellentesque non. Suspendisse id aliquet eros. Praesent efficitur vulputate ullamcorper. Proin ultrices nibh felis, ut iaculis elit dignissim quis. Phasellus lobortis enim ornare, vulputate elit vel, pretium lorem. Donec leo erat, bibendum non tellus nec, ornare ultricies massa. Aenean elementum neque sit amet ornare bibendum. Donec ultrices nibh risus, dictum scelerisque lectus vestibulum et. Aliquam sem risus, semper eget facilisis nec, sollicitudin quis nisi. Etiam vel tincidunt ipsum. Curabitur eu dui eleifend, luctus ipsum lobortis, euismod leo. Vivamus porta luctus tortor in efficitur. Phasellus pulvinar tincidunt quam, vitae imperdiet magna aliquam at. Nulla ut mattis odio.",
			"Donec id magna massa. Nullam malesuada, massa eget lobortis viverra, quam mi ullamcorper nisi, consectetur commodo dolor ante ut odio. Donec pulvinar eget metus vel sodales. Nunc non sem sit amet sapien ornare facilisis. Nam tellus diam, ornare nec turpis dictum, convallis malesuada elit. In sagittis tellus convallis, euismod enim at, efficitur enim. Curabitur ornare condimentum ligula, a tempus tellus consectetur at. Nunc nec felis at tellus imperdiet consequat id ut nisl. Proin blandit odio vel laoreet ultricies. Proin non pretium elit."
		).random().substring(0, nextInt(20, 250))
	}

	private fun randomAddress(): Triple<String, Double, Double> {
		return listOf(
			Triple("1 Whitehall St, New York, NY 10004, United States", 40.703780, -74.012720),
			Triple("38 Pearl St, New York, NY 10004, USA", 40.703400, -74.011650),
			Triple("63 Pearl St, New York, NY 10004, United States", 40.703740, -74.010700),
			Triple("60 Broad St, New York, NY 10004, United States", 40.705330, -74.012000),
			Triple("85 Broad St, New York, NY 10004, United States", 40.704201, -74.011017),
			Triple("45 Stone St, New York, NY 10004, United States", 40.704397, -74.0126032),
			Triple("17 State St 16 floor, New York, NY 10004, United States", 40.7027842, -74.0162479),
			Triple("1 Pearl St, New York, NY 10004, USA", 40.7033931, -74.0161949),
			Triple("2 Moore St, New York, NY 10004, USA", 40.7029918, -74.014545),
			Triple("2 Broadway, New York, NY 10004, United States", 40.7044122, -74.0150368)
		).random()
	}

	private fun randomPhotoName(): String {
		return listOf(
			"Front",
			"Kitchen",
			"Bedroom 1",
			"Bedroom 2",
			"Bedroom 3",
			"Bathroom",
			"Entrance",
			"Keeping Room",
			"Pantry",
			"Dining Room",
			"Living Room"
		).random()
	}

	private fun randomInterest(): String {
		return listOf(
			"Nearby elementary school, a supermarket and a bakery in the same street",
			"Nearby bakery",
			"College and high school within 10 minutes walking distance"
		).random()
	}

	fun randomDistrict(): String {
		return listOf("Manhattan", "Montauk", "Brooklyn", "Southampton", "Upper East Side", "Queens", "Staten Island", "Bronx", "New Jersey").random()
	}

	fun randomName(): String {
		return listOf("Bon", "Julia", "José", "Hamza", "Esteban", "Camille", "Marie", "Antoine").random()
	}

	fun randomDate(): Date {
		return GregorianCalendar(nextInt(2020, 2022), nextInt(0, 12), nextInt(0, 29)).time
	}

	private fun randomPhotoUrl(): String {
		return "https://picsum.photos/id/${nextInt(50)}/400"
	}

	inline fun <reified E : Enum<E>> randomEnum(): E {
		return enumValues<E>().random()
	}

	// ---------------------
	// Random Model
	// ---------------------
	private fun randomPhoto(): Photo {
		return Photo(name = randomPhotoName(), onlineUrl = randomPhotoUrl(), description = randomLoremIpsum())
	}

	private fun randomPhotoList(): List<Photo> {
		val list = mutableListOf<Photo>()
		repeat(nextInt(3, 10))
		{
			list.add(randomPhoto())
		}
		return Collections.unmodifiableList(list)
	}

	fun randomEstate(): Estate {
		val loc = randomAddress()
		return Estate(
			added = randomDate(),
			district = randomDistrict(),
			surface = nextInt(25, 1000),
			type = randomEnum(),
			description = randomLoremIpsum(),
			address = loc.first,
			latitude = loc.second,
			longitude = loc.third,
			agent = randomName(),
			sold = randomDate(),
			price = nextInt(100000, 5500000),
			photos = Collections.unmodifiableList(randomPhotoList()),
			bathrooms = nextInt(1, 4),
			rooms = nextInt(2, 10),
			bedrooms = nextInt(1, 5),
			interest = randomInterest(),
			status = randomEnum()
		)
	}

	// ---------------------
	// Public
	// ---------------------
	fun getRandomEstateList(): List<Estate> {
		val list = mutableListOf<Estate>()
		repeat(40)
		{
			list.add(randomEstate())
		}
		return Collections.unmodifiableList(list)
	}
}