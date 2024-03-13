package com.trudeals.utils

import com.trudeals.R
import com.trudeals.ui.home.dummydata.*
import com.trudeals.ui.isolated.dummydata.*

object DataUtils {
    fun setCustomerDescData(): ArrayList<String> {
        return ArrayList<String>().apply {
            add("Lorem ipsum dolor sit amet")
            add("Lorem ipsum dolor sit")
            add("Lorem ipsum dolor")
        }
    }

    fun setRealEstateDescData(): ArrayList<String> {
        return ArrayList<String>().apply {
            add("Lorem ipsum dolor sit amet")
            add("Lorem ipsum dolor sit")
            add("Lorem ipsum dolor")
        }
    }

    fun setBusinessOwnerDescData(): ArrayList<String> {
        return ArrayList<String>().apply {
            add("Lorem ipsum dolor sit amet")
            add("Lorem ipsum dolor sit")
            add("Lorem ipsum dolor")
        }
    }

    fun realEstateTabData(): ArrayList<RealEstateTabCU> {
        return ArrayList<RealEstateTabCU>().apply {
            add(RealEstateTabCU(title = "All", TabType.ALL, isSelected = true))
            add(RealEstateTabCU(title = "Home for Sale", TabType.HOME_FOR_SALE))
            add(RealEstateTabCU(title = "Rentals", TabType.RENTALS))
            add(RealEstateTabCU(title = "Vacation Rentals", TabType.VACATION_RENTALS))
        }
    }

    fun customerHomeRealEstateAllTabsData(): ArrayList<CustomerHomeListItem> {
        return ArrayList<CustomerHomeListItem>().apply {
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FEATURED_AND_FORSALE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "550,000"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    title = "New Apartment Nice View",
                    status = StatusType.FOR_RENT,
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "550,000"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
        }
    }


    fun customerHomeRealEstateRentalTabData(): ArrayList<CustomerHomeListItem> {
        return ArrayList<CustomerHomeListItem>().apply {
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FEATURED_AND_FORSALE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    title = "New Apartment Nice View",
                    status = StatusType.FOR_RENT,
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
        }
    }

    fun customerHomeRealEstateAndLocalDealTabsData(): ArrayList<CustomerHomeListItem> {
        return ArrayList<CustomerHomeListItem>().apply {
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FEATURED_AND_FORSALE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "550,000"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.FEATURED,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    title = "New Apartment Nice View",
                    status = StatusType.FOR_RENT,
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "550,000"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
        }
    }

    fun localDealsTabList(): ArrayList<LocalDealsChip> {
        return ArrayList<LocalDealsChip>().apply {
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_automotive,
                    title = "Automotive",
                    color = R.color.C_6F6DCA,
                    isSelected = true,
                    currentTabType = TabType.AUTOMOTIVE
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_beauty_and_spa,
                    title = "Beauty & Spa",
                    color = R.color.C_49B160,
                    currentTabType = TabType.BEAUTY_AND_SPA
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_food_and_drink,
                    title = "Food & Drink",
                    color = R.color.C_0A8B9C,
                    currentTabType = TabType.FOOD_AND_DRINK
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_health_fitness,
                    title = "Health & Fitness",
                    color = R.color.C_990A9C,
                    currentTabType = TabType.HEALTH_AND_FITNESS
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_home_and_garden,
                    title = "Home & Garden",
                    color = R.color.C_94860A,
                    currentTabType = TabType.HOME_AND_GARDEN
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_medical_service,
                    title = "Medical Service",
                    color = R.color.C_616567,
                    currentTabType = TabType.MEDICAL_SERVICE
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_services,
                    title = "Services",
                    color = R.color.C_DB594F,
                    currentTabType = TabType.SERVICES
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_things_to_do,
                    title = "Things to do",
                    color = R.color.C_009074,
                    currentTabType = TabType.THINGS_TO_DO
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_travel,
                    title = "Travel",
                    color = R.color.C_E34900,
                    currentTabType = TabType.TRAVEL
                )
            )
        }
    }

    fun customerHomeLoDealsAutomotiveTabsData(): ArrayList<CustomerHomeListItem> {
        return ArrayList<CustomerHomeListItem>().apply {
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.FEATURED,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.FEATURED,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
        }
    }

    fun customerHomeLoDealsBeautyAndSpaTabsData(): ArrayList<CustomerHomeListItem> {
        //for beauty and spa - location view will contain service name and description view will contain location
        return ArrayList<CustomerHomeListItem>().apply {
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_massage,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "110-Minute Luxury Spa Massage",
                    location = "Xanadu Med Spa",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "200"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_wax,
                    status = StatusType.NONE,
                    discount = "10% OFF",
                    title = "Eye  and Lip Wax",
                    location = "Urban Glo Skincare & Waxing",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "100"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_massage,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "110-Minute Luxury Spa Massage",
                    location = "Xanadu Med Spa",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "200"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_wax,
                    status = StatusType.NONE,
                    discount = "10% OFF",
                    title = "Eye  and Lip Wax",
                    location = "Urban Glo Skincare & Waxing",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "100"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_massage,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "110-Minute Luxury Spa Massage",
                    location = "Xanadu Med Spa",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "200"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_wax,
                    status = StatusType.NONE,
                    discount = "10% OFF",
                    title = "Eye  and Lip Wax",
                    location = "Urban Glo Skincare & Waxing",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "100"
                )
            )
        }
    }

    fun realEstateDetailsImages(): ArrayList<Int> {
        return ArrayList<Int>().apply {
            add(R.drawable.dummy_image_listing)
            add(R.drawable.dummy_image_listing)
            add(R.drawable.dummy_image_listing)
            add(R.drawable.dummy_image_listing)
            add(R.drawable.dummy_image_listing)
        }
    }

    fun automotiveImages(): ArrayList<Int> {
        return ArrayList<Int>().apply {
            add(R.drawable.dummy_image_automotive)
            add(R.drawable.dummy_image_automotive)
            add(R.drawable.dummy_image_automotive)
            add(R.drawable.dummy_image_automotive)
            add(R.drawable.dummy_image_automotive)
        }
    }

    fun propertyDetails(): ArrayList<PropertyDetails> {
        return ArrayList<PropertyDetails>().apply {
            add(
                PropertyDetails(
                    title = "Features",
                    tag = PropertyDetailsTag.FEATURES,
                    featuresList = featuresOfProperty()
                )
            )
            add(
                PropertyDetails(
                    title = "Property Area and specifications",
                    tag = PropertyDetailsTag.PROPERTY_AREA_AND_SPECIFICATIONS,
                    areaAndSpecifications = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sit amet dapibus massa. Proin sem sapien, blandit sit amet varius sed, facilisis ut tellus. Curabitur et quam erat. Sed blandit leo turpis, a posuere augue blandit ut.\n" +
                            "\n" +
                            "Sed id posuere leo. Sed blandit dolor tincidunt fermentum maximus. Quisque tincidunt convallis viverra. Nullam eget nulla mattis, luctus orci id, fermentum enim. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Phasellus eleifend ipsum in orci condimentum vestibulum. \n" +
                            "\n" +
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sit amet dapibus massa. Proin sem sapien, blandit sit amet varius sed, facilisis ut tellus. Curabitur et quam erat. Sed blandit leo turpis, a posuere augue blandit ut.\n" +
                            "\n" +
                            "Maecenas lacinia lobortis massa at finibus."
                )
            )
            add(
                PropertyDetails(
                    title = "Property Images",
                    tag = PropertyDetailsTag.PROPERTY_IMAGES,
                    imagesList = propertyImages()
                )
            )
            add(
                PropertyDetails(
                    "Property Video",
                    tag = PropertyDetailsTag.PROPERTY_VIDEO,
                    imageForVideo = R.drawable.dummy_image_listing
                )
            )
        }
    }

    fun contactUsDetails(): ArrayList<ContactUs> {
        return ArrayList<ContactUs>().apply {
            add(
                ContactUs(
                    icon = R.drawable.ic_phone_black_outlined,
                    detail = "+1 1212121212",
                    onClick = OnClick.CONTACT,
                    isTextUnderlineSpan = true
                )
            )
            add(
                ContactUs(
                    icon = R.drawable.ic_location_black_outlined,
                    onClick = OnClick.ADDRESS,
                    detail = "805-807 N Avalon St, Memphis, TN 38107 Listed by Worth W Woodyard with Woodyard Realty Corp"
                )
            )
            add(
                ContactUs(
                    icon = R.drawable.ic_domain_black_outlined,
                    onClick = OnClick.WEBSITE,
                    detail = "www.ondmand.com"
                )
            )
            add(
                ContactUs(
                    icon = R.drawable.ic_email_black_outlined,
                    onClick = OnClick.EMAIL_ID,
                    detail = "abc@gmail.com"
                )
            )
        }
    }

    fun popularSearches(): ArrayList<String> {
        return ArrayList<String>().apply {
            add("Spa")
            add("Oil")
            add("Massage")
            add("Cuisine")
            add("Personalized")
            add("Micro")
            add("Mind")
        }
    }

    fun realEstateFilteredList(): ArrayList<CustomerHomeListItem> {
        return ArrayList<CustomerHomeListItem>().apply {
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.NONE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.NONE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.NONE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    title = "New Apartment Nice View",
                    status = StatusType.NONE,
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "550,000"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.NONE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
        }
    }

    fun propertyType(): ArrayList<PropertyTypeChip> {
        return ArrayList<PropertyTypeChip>().apply {
            add(PropertyTypeChip(chip = "All", isSelected = true))
            add(PropertyTypeChip(chip = "Single Family Home"))
            add(PropertyTypeChip(chip = "Condo"))
            add(PropertyTypeChip(chip = "Land"))
            add(PropertyTypeChip(chip = "Townhouse"))
            add(PropertyTypeChip(chip = "Multi Family"))
            add(PropertyTypeChip(chip = "Farm"))
        }
    }

    fun livingStatus(): ArrayList<LivingStatus> {
        return ArrayList<LivingStatus>().apply {
            add(LivingStatus(status = "Any - For Sale"))
            add(LivingStatus(status = "New Construction"))
            add(LivingStatus(status = "Existing Homes"))
            add(LivingStatus(status = "Foreclosures"))
            add(LivingStatus(status = "Recently Sold"))
            add(LivingStatus(status = "Hide Foreclosurest"))
        }
    }

    fun sortBy(): ArrayList<SortBy> {
        return ArrayList<SortBy>().apply {
            add(SortBy(option = "All", isSelected = true))
            add(SortBy(option = "Featured"))
            add(SortBy(option = "Price, Low to High"))
            add(SortBy(option = "Price, High to Low"))
            add(SortBy(option = "Alphabetically, A-Z"))
            add(SortBy(option = "Alphabetically, Z-A"))
        }
    }

    fun chatList(): ArrayList<ChatData> {
        return ArrayList<ChatData>().apply {
            add(
                ChatData(
                    profileImage = R.drawable.dummy_image_chat_profile,
                    userName = "Arieta",
                    lastMsg = "Same to you!",
                    unReadMsgCount = "1",
                    lastMsgTextColor = R.color.C_ED1D26,
                    time = "02:54 Am"
                )
            )
            add(
                ChatData(
                    profileImage = R.drawable.dummy_image_chat_profile,
                    userName = "Asami Sato",
                    lastMsg = "Hey Beautiful",
                    unReadMsgCount = "1",
                    lastMsgTextColor = R.color.C_ED1D26,
                    time = "Yesterday"
                )
            )
            add(
                ChatData(
                    profileImage = R.drawable.dummy_image_chat_profile,
                    userName = "Arieta Benson",
                    lastMsg = "Hey Beautiful",
                    lastMsgTextColor = R.color.C_3361D0,
                    time = "Yesterday 10:45 PM"
                )
            )
            add(
                ChatData(
                    profileImage = R.drawable.dummy_image_chat_profile,
                    userName = "Asami Sato",
                    lastMsg = "Hey Beautiful",
                    lastMsgTextColor = R.color.C_ED1D26,
                    time = "26 Jun 02:54 AM"
                )
            )
        }
    }

    fun notificationList(): ArrayList<Notification> {
        return ArrayList<Notification>().apply {
            add(
                Notification(
                    date = "31 Jan 2022",
                    subData = ArrayList<SubNotification>().apply {
                        add(
                            (SubNotification(
                                time = "02:22 PM",
                                title = "Lorem ipsum dolor sit",
                                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce volutpat ligula id dignissim congue. Mauris ut."
                            ))
                        )
                        add(
                            (SubNotification(
                                time = "04:24 PM",
                                title = "Lorem ipsum dolor sit",
                                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce volutpat ligula id dignissim congue. Mauris ut."
                            ))
                        )
                    }
                )
            )
            add(
                Notification(
                    date = "30 Jan 2022",
                    subData = ArrayList<SubNotification>().apply {
                        add(
                            (SubNotification(
                                time = "02:22 PM",
                                title = "Lorem ipsum dolor sit",
                                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce volutpat ligula id dignissim congue. Mauris ut."
                            ))
                        )
                        add(
                            (SubNotification(
                                time = "04:24 PM",
                                title = "Lorem ipsum dolor sit",
                                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce volutpat ligula id dignissim congue. Mauris ut."
                            ))
                        )
                        add(
                            (SubNotification(
                                time = "04:24 PM",
                                title = "Lorem ipsum dolor sit",
                                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce volutpat ligula id dignissim congue. Mauris ut."
                            ))
                        )
                    }
                )
            )
            add(
                Notification(
                    date = "29 Jan 2022",
                    subData = ArrayList<SubNotification>().apply {
                        add(
                            (SubNotification(
                                time = "02:22 PM",
                                title = "Lorem ipsum dolor sit",
                                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce volutpat ligula id dignissim congue. Mauris ut."
                            ))
                        )
                        add(
                            (SubNotification(
                                time = "04:24 PM",
                                title = "Lorem ipsum dolor sit",
                                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce volutpat ligula id dignissim congue. Mauris ut."
                            ))
                        )
                        add(
                            (SubNotification(
                                time = "04:24 PM",
                                title = "Lorem ipsum dolor sit",
                                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce volutpat ligula id dignissim congue. Mauris ut."
                            ))
                        )
                    }
                )
            )
        }
    }

    fun minPrice(): ArrayList<MinMaxPrice> {
        return ArrayList<MinMaxPrice>().apply {
            add(MinMaxPrice(option = "10K", isSelected = false))
            add(MinMaxPrice(option = "11K", isSelected = false))
            add(MinMaxPrice(option = "12K", isSelected = false))
            add(MinMaxPrice(option = "13K", isSelected = false))
            add(MinMaxPrice(option = "14K", isSelected = false))
            add(MinMaxPrice(option = "15K", isSelected = false))
        }
    }

    fun maxPrice(): ArrayList<MinMaxPrice> {
        return ArrayList<MinMaxPrice>().apply {
            add(MinMaxPrice(option = "15K", isSelected = false))
            add(MinMaxPrice(option = "16K", isSelected = false))
            add(MinMaxPrice(option = "17K", isSelected = false))
            add(MinMaxPrice(option = "18K", isSelected = false))
            add(MinMaxPrice(option = "19K", isSelected = false))
            add(MinMaxPrice(option = "20K", isSelected = false))
        }
    }

    fun selectBeds(): ArrayList<SelectBeds> {
        return ArrayList<SelectBeds>().apply {
            add(SelectBeds(option = "1", isSelected = false))
            add(SelectBeds(option = "2", isSelected = false))
            add(SelectBeds(option = "3", isSelected = false))
            add(SelectBeds(option = "4", isSelected = false))
            add(SelectBeds(option = "5", isSelected = false))
            add(SelectBeds(option = "6+", isSelected = false))
        }
    }

    fun selectBathrooms(): ArrayList<SelectBathrooms> {
        return ArrayList<SelectBathrooms>().apply {
            add(SelectBathrooms(option = "1", isSelected = false))
            add(SelectBathrooms(option = "2", isSelected = false))
            add(SelectBathrooms(option = "3", isSelected = false))
            add(SelectBathrooms(option = "4", isSelected = false))
            add(SelectBathrooms(option = "5", isSelected = false))
            add(SelectBathrooms(option = "6+", isSelected = false))
        }
    }

    fun selectGarageSize(): ArrayList<SelectGarageSize> {
        return ArrayList<SelectGarageSize>().apply {
            add(SelectGarageSize(option = "1 Car", isSelected = false))
            add(SelectGarageSize(option = "2 Cars", isSelected = false))
            add(SelectGarageSize(option = "3 Cars", isSelected = false))
            add(SelectGarageSize(option = "4+ Cars", isSelected = false))
        }
    }

    fun selectAreaUnit(): ArrayList<SelectAreaUnit> {
        return ArrayList<SelectAreaUnit>().apply {
            add(SelectAreaUnit(option = "Sq Ft", isSelected = false))
            add(SelectAreaUnit(option = "Ft", isSelected = false))
        }
    }

    fun propertyTypeOptions(): ArrayList<SelectPropertyType> {
        return ArrayList<SelectPropertyType>().apply {
            add(SelectPropertyType(option = "All", isSelected = false))
            add(SelectPropertyType(option = "Single Family Home", isSelected = false))
            add(SelectPropertyType(option = "Condo", isSelected = false))
            add(SelectPropertyType(option = "Land", isSelected = false))
            add(SelectPropertyType(option = "Townhouse", isSelected = false))
            add(SelectPropertyType(option = "Multi Family", isSelected = false))
            add(SelectPropertyType(option = "Farm", isSelected = false))
        }
    }

    fun localDealDetailsImages(): ArrayList<Int> {
        return ArrayList<Int>().apply {
            add(R.drawable.dummy_image_beauty_and_spa_wax)
            add(R.drawable.dummy_image_beauty_and_spa_wax)
            add(R.drawable.dummy_image_beauty_and_spa_wax)
            add(R.drawable.dummy_image_beauty_and_spa_wax)
            add(R.drawable.dummy_image_beauty_and_spa_wax)
        }
    }

    fun businessHours(): ArrayList<BusinessHours> {
        return ArrayList<BusinessHours>().apply {
            add(BusinessHours(day = "Sunday", isClosed = true))
            add(
                BusinessHours(
                    day = "Monday",
                    listOfTimeSlots = listOfTimeSlots()
                )
            )
            add(
                BusinessHours(day = "Tuesday",
                    listOfTimeSlots = ArrayList<String>().apply { add("10:00 am - 06:00 pm") })
            )
            add(BusinessHours(day = "Wednesday", listOfTimeSlots = listOfTimeSlots()))
            add(BusinessHours(day = "Thursday", listOfTimeSlots = listOfTimeSlots()))
            add(
                BusinessHours(day = "Friday",
                    listOfTimeSlots = ArrayList<String>().apply { add("10:00 am - 06:00 pm") })
            )
            add(BusinessHours(day = "Saturday", isClosed = true))
        }
    }

    private fun listOfTimeSlots(): ArrayList<String> {
        return ArrayList<String>().apply {
            add("10:00 am - 02:00 pm")
            add("03:00 am - 06:00 pm")

        }
    }

    fun localDealsFilteredList(): ArrayList<CustomerHomeListItem> {
        return ArrayList<CustomerHomeListItem>().apply {
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.FEATURED,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.FEATURED,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
        }
    }

    fun allCategoryLocalDeals(): ArrayList<LocalDealsChip> {
        return ArrayList<LocalDealsChip>().apply {
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_automotive,
                    title = "Automotive",
                    color = R.color.C_6F6DCA,
                    isSelected = true,
                    currentTabType = TabType.AUTOMOTIVE
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_beauty_and_spa,
                    title = "Beauty & Spa",
                    color = R.color.C_49B160,
                    currentTabType = TabType.BEAUTY_AND_SPA
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_food_and_drink,
                    title = "Food & Drink",
                    color = R.color.C_0A8B9C,
                    currentTabType = TabType.FOOD_AND_DRINK
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_health_fitness,
                    title = "Health & Fitness",
                    color = R.color.C_990A9C,
                    currentTabType = TabType.HEALTH_AND_FITNESS
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_home_and_garden,
                    title = "Home & Garden",
                    color = R.color.C_94860A,
                    currentTabType = TabType.HOME_AND_GARDEN
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_medical_service,
                    title = "Medical Service",
                    color = R.color.C_616567,
                    currentTabType = TabType.MEDICAL_SERVICE
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_services,
                    title = "Services",
                    color = R.color.C_DB594F,
                    currentTabType = TabType.SERVICES
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_things_to_do,
                    title = "Things to do",
                    color = R.color.C_009074,
                    currentTabType = TabType.THINGS_TO_DO
                )
            )
            add(
                LocalDealsChip(
                    icon = R.drawable.ic_travel,
                    title = "Travel",
                    color = R.color.C_E34900,
                    currentTabType = TabType.TRAVEL
                )
            )
        }
    }

    fun localDealBeautyAndSpaCategoryData(): ArrayList<CustomerHomeListItem> {
        return ArrayList<CustomerHomeListItem>().apply {
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_massage,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "110-Minute Luxury Spa Massage",
                    location = "Xanadu Med Spa",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "200"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_wax,
                    status = StatusType.NONE,
                    discount = "10% OFF",
                    title = "Eye  and Lip Wax",
                    location = "Urban Glo Skincare & Waxing",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "100"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_massage,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "110-Minute Luxury Spa Massage",
                    location = "Xanadu Med Spa",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "200"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_wax,
                    status = StatusType.NONE,
                    discount = "10% OFF",
                    title = "Eye  and Lip Wax",
                    location = "Urban Glo Skincare & Waxing",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "100"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_massage,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "110-Minute Luxury Spa Massage",
                    location = "Xanadu Med Spa",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "200"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_beauty_and_spa_wax,
                    status = StatusType.NONE,
                    discount = "10% OFF",
                    title = "Eye  and Lip Wax",
                    location = "Urban Glo Skincare & Waxing",
                    description = "Quincy St, Brooklyn, NY, USA",
                    amount = "100"
                )
            )
        }
    }

    fun localDealAutomotiveCategoryData(): ArrayList<CustomerHomeListItem> {
        return ArrayList<CustomerHomeListItem>().apply {
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.FEATURED,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.FEATURED,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
        }
    }

    fun chatMoreOption(): ArrayList<ChatMoreOption> {
        return ArrayList<ChatMoreOption>().apply {
            add(
                ChatMoreOption(
                    icon = R.drawable.ic_profile,
                    option = "See Profile",
                    tag = MoreOption.SEE_PROFILE
                )
            )
            add(
                ChatMoreOption(
                    icon = R.drawable.ic_block,
                    option = "Block",
                    tag = MoreOption.BLOCK
                )
            )
            add(
                ChatMoreOption(
                    icon = R.drawable.ic_delete,
                    option = "Delete Chat",
                    tag = MoreOption.DELETE_CHAT
                )
            )
        }
    }

    fun documentPdfs(): ArrayList<String> {
        return ArrayList<String>().apply {
            add("Listing Brochure.pdf")
            add("Florida Real Estate Contract.pdf")
            add("Offer Sheet")
        }
    }

    fun customerNavigationDrawerOption(): ArrayList<NavigationDrawerOption> {
        return ArrayList<NavigationDrawerOption>().apply {
            add(
                NavigationDrawerOption(
                    option = " Change Password",
                    icon = R.drawable.ic_lock,
                    onClick = OnClick.CHANGE_PASSWORD
                )
            )
            add(
                NavigationDrawerOption(
                    option = "My Favorite Real Estate",
                    icon = R.drawable.ic_heart_selected_small,
                    onClick = OnClick.MY_FAV_REAL_ESTATE
                )
            )
            add(
                NavigationDrawerOption(
                    option = "My Favorite Deals",
                    icon = R.drawable.ic_heart_selected_small,
                    onClick = OnClick.MY_FAV_DEALS
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Delete Account",
                    icon = R.drawable.ic_delete_acc,
                    onClick = OnClick.DELETE_ACCOUNT
                )
            )
            add(
                NavigationDrawerOption(
                    option = "News",
                    icon = R.drawable.ic_news,
                    onClick = OnClick.NEWS
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Rate App",
                    icon = R.drawable.ic_star,
                    OnClick.RATE_APP
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Share App",
                    icon = R.drawable.ic_share,
                    OnClick.SHARE_APP
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Help & FAQ",
                    icon = R.drawable.ic_faq,
                    onClick = OnClick.HELP_AND_FAQ
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Advertise with us",
                    icon = R.drawable.ic_advertise,
                    onClick = OnClick.ADVERTISE_WITH_US
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Terms & Privacy Policy",
                    icon = R.drawable.ic_terms_and_pp,
                    onClick = OnClick.TERMS_AND_PRIVACY_POLICY
                )
            )
            add(
                NavigationDrawerOption(
                    option = "About Us",
                    icon = R.drawable.ic_about_us,
                    onClick = OnClick.ABOUT_US
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Contact Us",
                    icon = R.drawable.ic_contact_us,
                    onClick = OnClick.CONTACT_US
                )
            )
        }
    }

    fun realEstateFavListCU(): ArrayList<CustomerHomeListItem> {
        return ArrayList<CustomerHomeListItem>().apply {
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.NONE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month",
                    isLiked = true
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.NONE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month",
                    isLiked = true
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.NONE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month",
                    isLiked = true
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    title = "New Apartment Nice View",
                    status = StatusType.NONE,
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "550,000",
                    isLiked = true
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.NONE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month",
                    isLiked = true
                )
            )
        }
    }

    fun localDealsFavListCU(): ArrayList<CustomerHomeListItem> {
        return ArrayList<CustomerHomeListItem>().apply {
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.FEATURED,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15",
                    isLiked = true
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15",
                    isLiked = true
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15",
                    isLiked = true
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.FEATURED,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15",
                    isLiked = true
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.DISCOUNT,
                    discount = "10% OFF",
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15",
                    isLiked = true
                )
            )
            add(
                CustomerHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15",
                    isLiked = true
                )
            )
        }
    }

    fun newsList(): ArrayList<NewsList> {
        return ArrayList<NewsList>().apply {
            add(
                NewsList(
                    title = "Add Title Here",
                    postedOn = "26 Jan 2023",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus."
                )
            )
            add(
                NewsList(
                    title = "Add Title Here",
                    postedOn = "26 Jan 2023",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus."
                )
            )
            add(
                NewsList(
                    title = "Add Title Here",
                    postedOn = "26 Jan 2023",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus."
                )
            )
            add(
                NewsList(
                    title = "Add Title Here",
                    postedOn = "26 Jan 2023",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus."
                )
            )
            add(
                NewsList(
                    title = "Add Title Here",
                    postedOn = "26 Jan 2023",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus."
                )
            )
        }
    }

    fun requestedList(): ArrayList<RequestList> {
        return ArrayList<RequestList>().apply {
            add(
                RequestList(
                    userName = "John Doe Requested",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.REQUESTED
                )
            )
            add(
                RequestList(
                    userName = "John Doe Requested",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.REQUESTED
                )
            )
            add(
                RequestList(
                    userName = "John Doe Requested",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.REQUESTED
                )
            )
            add(
                RequestList(
                    userName = "John Doe Requested",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.REQUESTED
                )
            )
            add(
                RequestList(
                    userName = "John Doe Requested",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.REQUESTED
                )
            )
        }
    }

    fun acceptedList(): ArrayList<RequestList> {
        return ArrayList<RequestList>().apply {
            add(
                RequestList(
                    userName = "John Doe Accepted",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.ACCEPTED
                )
            )
            add(
                RequestList(
                    userName = "John Doe Accepted",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.ACCEPTED
                )
            )
            add(
                RequestList(
                    userName = "John Doe Accepted",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.ACCEPTED
                )
            )
            add(
                RequestList(
                    userName = "John Doe Accepted",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.ACCEPTED
                )
            )
        }
    }

    fun completedList(): ArrayList<RequestList> {
        return ArrayList<RequestList>().apply {
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.COMPLETED
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.COMPLETED
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.COMPLETED
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.COMPLETED
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    requestType = RequestCategoryType.COMPLETED
                )
            )
        }
    }

    fun userSelection(): ArrayList<UserSelection> {
        return ArrayList<UserSelection>().apply {
            add(
                UserSelection(
                    icon = R.drawable.ic_customer_user,
                    user = "User",
                    userType = UserType.CUSTOMER_USER
                )
            )
            add(
                UserSelection(
                    icon = R.drawable.ic_real_estate,
                    user = "Real Estate",
                    userType = UserType.REAL_ESTATE_USER
                )
            )
            add(
                UserSelection(
                    icon = R.drawable.ic_business_advertising,
                    user = "Business Advertising",
                    userType = UserType.BUSINESS_USER
                )
            )
        }
    }

    fun realEstateNavigationDrawerOption(): ArrayList<NavigationDrawerOption> {
        return ArrayList<NavigationDrawerOption>().apply {
            add(
                NavigationDrawerOption(
                    option = " Change Password",
                    icon = R.drawable.ic_lock,
                    onClick = OnClick.CHANGE_PASSWORD
                )
            )
            add(
                NavigationDrawerOption(
                    option = "My Listings",
                    icon = R.drawable.ic_heart_selected_small,
                    onClick = OnClick.MY_LISTING
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Delete Account",
                    icon = R.drawable.ic_delete_acc,
                    onClick = OnClick.DELETE_ACCOUNT
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Rate App",
                    icon = R.drawable.ic_star,
                    OnClick.RATE_APP
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Share App",
                    icon = R.drawable.ic_share,
                    OnClick.SHARE_APP
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Help & FAQ",
                    icon = R.drawable.ic_faq,
                    onClick = OnClick.HELP_AND_FAQ
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Terms & Privacy Policy",
                    icon = R.drawable.ic_terms_and_pp,
                    onClick = OnClick.TERMS_AND_PRIVACY_POLICY
                )
            )
            add(
                NavigationDrawerOption(
                    option = "About Us",
                    icon = R.drawable.ic_about_us,
                    onClick = OnClick.ABOUT_US
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Contact Us",
                    icon = R.drawable.ic_contact_us,
                    onClick = OnClick.CONTACT_US
                )
            )
        }
    }

    fun realEstateHomeChip(): ArrayList<RealEstateOrBusinessHomeChip> {
        return ArrayList<RealEstateOrBusinessHomeChip>().apply {
            add(
                RealEstateOrBusinessHomeChip(
                    icon = R.drawable.ic_building_red,
                    title = "My Listing",
                    color = R.color.C_ED1D26,
                    chip = RealEstateOrBusinessChipType.MY_LISTING
                )
            )
            add(
                RealEstateOrBusinessHomeChip(
                    icon = R.drawable.ic_schedule_blue,
                    title = "Schedule an Open House",
                    color = R.color.C_00A9DE,
                    chip = RealEstateOrBusinessChipType.SCHEDULE_AN_OPEN_HOUSE
                )
            )
            add(
                RealEstateOrBusinessHomeChip(
                    icon = R.drawable.ic_booking_request,
                    title = "Booking Request",
                    color = R.color.C_04D400,
                    badgeCount = 5,
                    chip = RealEstateOrBusinessChipType.BOOKING_REQUEST
                )
            )
            add(
                RealEstateOrBusinessHomeChip(
                    icon = R.drawable.ic_chat_purple,
                    title = "Chat",
                    color = R.color.C_C361FF,
                    badgeCount = 10,
                    chip = RealEstateOrBusinessChipType.CHAT
                )
            )
        }
    }

    fun realEstateHomeTabData(): ArrayList<RealEstateTabREU> {
        return ArrayList<RealEstateTabREU>().apply {
            add(RealEstateTabREU(title = "All", TabType.ALL, true))
            add(RealEstateTabREU(title = "Home for Sale", TabType.HOME_FOR_SALE))
            add(RealEstateTabREU(title = "Rentals", TabType.RENTALS))
            add(RealEstateTabREU(title = "Vacation Rentals", TabType.VACATION_RENTALS))
        }
    }

    fun imageLibTabData(): ArrayList<RealEstateTabREU> {
        return ArrayList<RealEstateTabREU>().apply {
            add(RealEstateTabREU(title = "Automotive", TabType.AUTOMOTIVE, true))
            add(RealEstateTabREU(title = "Saloon", TabType.SALON))
            add(RealEstateTabREU(title = "Food & Drink", TabType.FOOD_AND_DRINK))
            add(RealEstateTabREU(title = "Health & Fitness", TabType.HEALTH_AND_FITNESS))
        }
    }

    fun realEstateHomeAllTabsData(): ArrayList<RealEstateHomeListItem> {
        return ArrayList<RealEstateHomeListItem>().apply {
            add(
                RealEstateHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_SALE,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "550,000"
                )
            )
            add(
                RealEstateHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                RealEstateHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_VACATION_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
            add(
                RealEstateHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    title = "New Apartment Nice View",
                    status = StatusType.FOR_SALE,
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "550,000"
                )
            )
            add(
                RealEstateHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month"
                )
            )
        }
    }

    fun realEstatePropertyDetails(): ArrayList<PropertyDetails> {
        return ArrayList<PropertyDetails>().apply {
            /*add(
                PropertyDetails(
                    title = "Features",
                    tag = PropertyDetailsTag.FEATURES,
                    featuresList = featuresOfProperty()
                )
            )
            add(
                PropertyDetails(
                    title = "Property Area and specifications",
                    tag = PropertyDetailsTag.PROPERTY_AREA_AND_SPECIFICATIONS,
                    areaAndSpecifications = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sit amet dapibus massa. Proin sem sapien, blandit sit amet varius sed, facilisis ut tellus. Curabitur et quam erat. Sed blandit leo turpis, a posuere augue blandit ut.\n" +
                            "\n" +
                            "Sed id posuere leo. Sed blandit dolor tincidunt fermentum maximus. Quisque tincidunt convallis viverra. Nullam eget nulla mattis, luctus orci id, fermentum enim. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Phasellus eleifend ipsum in orci condimentum vestibulum. \n" +
                            "\n" +
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sit amet dapibus massa. Proin sem sapien, blandit sit amet varius sed, facilisis ut tellus. Curabitur et quam erat. Sed blandit leo turpis, a posuere augue blandit ut.\n" +
                            "\n" +
                            "Maecenas lacinia lobortis massa at finibus."
                )
            )*/
            add(
                PropertyDetails(
                    title = "Property Images",
                    tag = PropertyDetailsTag.PROPERTY_IMAGES,
                    imagesList = propertyImages()
                )
            )
            add(
                PropertyDetails(
                    "Property Video",
                    tag = PropertyDetailsTag.PROPERTY_VIDEO,
                    imageForVideo = R.drawable.dummy_image_listing
                )
            )
        }
    }

    private fun featuresOfProperty(): ArrayList<String> {
        return ArrayList<String>().apply {
            add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
            add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
            add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
            add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
            add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
            add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
        }
    }

    private fun propertyImages(): ArrayList<Int> {
        return ArrayList<Int>().apply {
            add(R.drawable.dummy_image_listing)
            add(R.drawable.dummy_image_listing)
            add(R.drawable.dummy_image_listing)
        }
    }

    fun realEstateRequestList(): ArrayList<RealEstateRequestList> {
        return ArrayList<RealEstateRequestList>().apply {
            add(
                RealEstateRequestList(
                    userName = "John Doe",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE
                )
            )
            add(
                RealEstateRequestList(
                    userName = "John Doe",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_RENT
                )
            )
            add(
                RealEstateRequestList(
                    userName = "John Doe",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_VACATION_RENT
                )
            )
        }
    }

    fun propertyListTab(): ArrayList<PropertyListTab> {
        return ArrayList<PropertyListTab>().apply {
            add(
                PropertyListTab(
                    tab = "Home For Sale",
                    tag = PropertyListTag.HOME_FOR_SALE,
                    isSelected = true
                )
            )
            add(PropertyListTab(tab = "Home For Rent", tag = PropertyListTag.HOME_FOR_RENT))
            add(PropertyListTab(tab = "Vacation Rental", tag = PropertyListTag.VACATION_RENTAL))
        }
    }


    fun SubCategoryType.getHomeListBasedOnCategory(currentTab: TabType): ArrayList<CustomerHomeListItem> {
        when (this) {
            SubCategoryType.DEALS_AND_REAL_ESTATE -> {
                return customerHomeRealEstateAndLocalDealTabsData()
            }
            SubCategoryType.NONE -> {
                when (currentTab) {
                    TabType.ALL -> {
                        //real estate each tab data - currently set same data
                        return customerHomeRealEstateAllTabsData()
                    }
                    TabType.HOME_FOR_SALE -> {
                        return customerHomeRealEstateAllTabsData()
                    }
                    TabType.RENTALS -> {
                        return customerHomeRealEstateRentalTabData()
                    }
                    TabType.VACATION_RENTALS -> {
                        return customerHomeRealEstateAllTabsData()
                    }
                    TabType.AUTOMOTIVE -> {
                        return customerHomeLoDealsAutomotiveTabsData()
                    }
                    TabType.BEAUTY_AND_SPA -> {
                        return customerHomeLoDealsBeautyAndSpaTabsData()
                    }
                    TabType.FOOD_AND_DRINK -> {
                        return customerHomeLoDealsAutomotiveTabsData()
                    }
                    TabType.HEALTH_AND_FITNESS -> {
                        return customerHomeLoDealsBeautyAndSpaTabsData()
                    }
                    TabType.HOME_AND_GARDEN -> {
                        return customerHomeLoDealsAutomotiveTabsData()
                    }
                    TabType.MEDICAL_SERVICE -> {
                        return customerHomeLoDealsBeautyAndSpaTabsData()
                    }
                    TabType.SERVICES -> {
                        return customerHomeLoDealsAutomotiveTabsData()
                    }
                    TabType.THINGS_TO_DO -> {
                        return customerHomeLoDealsBeautyAndSpaTabsData()
                    }
                    TabType.TRAVEL -> {
                        return customerHomeLoDealsAutomotiveTabsData()
                    }
                    else -> {
                        return customerHomeRealEstateAllTabsData()
                    }
                }
            }
            else -> {
                return customerHomeRealEstateAllTabsData()
            }
        }
    }

    fun realEstateHomeForSalePropertyList(): ArrayList<RealEstatePropertyList> {
        return ArrayList<RealEstatePropertyList>().apply {
            add(
                RealEstatePropertyList(
                    title = "New Apartment Nice View",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "550,000",
                    status = StatusType.FOR_SALE
                )
            )
            add(
                RealEstatePropertyList(
                    title = "New Apartment Nice View",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "550,000",
                    status = StatusType.FOR_SALE
                )
            )
            add(
                RealEstatePropertyList(
                    title = "New Apartment Nice View",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "550,000",
                    status = StatusType.FOR_SALE
                )
            )
            add(
                RealEstatePropertyList(
                    title = "New Apartment Nice View",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "550,000",
                    status = StatusType.FOR_SALE
                )
            )
        }
    }

    fun realEstateHomeForRentPropertyList(): ArrayList<RealEstatePropertyList> {
        return ArrayList<RealEstatePropertyList>().apply {
            add(
                RealEstatePropertyList(
                    title = "New Apartment Rent",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "7,500/Month",
                    status = StatusType.FOR_RENT
                )
            )
            add(
                RealEstatePropertyList(
                    title = "New Apartment Rent",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "7,500/Month",
                    status = StatusType.FOR_RENT
                )
            )
            add(
                RealEstatePropertyList(
                    title = "New Apartment Rent",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "7,500/Month",
                    status = StatusType.FOR_RENT
                )
            )
            add(
                RealEstatePropertyList(
                    title = "New Apartment Rent",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "7,500/Month",
                    status = StatusType.FOR_RENT
                )
            )
        }
    }

    fun realEstateVacationRentalPropertyList(): ArrayList<RealEstatePropertyList> {
        return ArrayList<RealEstatePropertyList>().apply {
            add(
                RealEstatePropertyList(
                    title = "New Apartment Nice View",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "550,000",
                    status = StatusType.FOR_VACATION_RENT
                )
            )
            add(
                RealEstatePropertyList(
                    title = "New Apartment Nice View",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "550,000",
                    status = StatusType.FOR_VACATION_RENT
                )
            )
            add(
                RealEstatePropertyList(
                    title = "New Apartment Nice View",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "550,000",
                    status = StatusType.FOR_VACATION_RENT
                )
            )
            add(
                RealEstatePropertyList(
                    title = "New Apartment Nice View",
                    image = R.drawable.dummy_image_listing,
                    address = "Quincy St, Brooklyn, NY, USA",
                    amenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfSqFt = "1200"
                    ),
                    price = "550,000",
                    status = StatusType.FOR_VACATION_RENT
                )
            )
        }
    }

    fun setSteps(): ArrayList<Steps> {
        return ArrayList<Steps>().apply {
            add(Steps("1", isSelected = true))
            add(Steps("2"))
        }
    }

    fun setBusinessProfileSteps(): ArrayList<Steps> {
        return ArrayList<Steps>().apply {
            add(Steps("1", isSelected = true))
            add(Steps("2"))
            add(Steps("3"))
            add(Steps("4"))
        }
    }

    fun rentDurationTypeOptions(): ArrayList<SelectRentDurationType> {
        return ArrayList<SelectRentDurationType>().apply {
            add(
                SelectRentDurationType(
                    option = "Short Term",
                    isSelected = false,
                    tag = PropertyListTag.HOME_FOR_RENT
                )
            )
            add(
                SelectRentDurationType(
                    option = "Long Term",
                    isSelected = false,
                    tag = PropertyListTag.HOME_FOR_RENT
                )
            )
            /*add(
                SelectRentDurationType(
                    option = "Vacation Rental",
                    isSelected = false,
                    tag = PropertyListTag.VACATION_RENTAL
                )
            )*/
        }
    }

    fun vacationRentDurationTypeOptions(): ArrayList<SelectRentDurationType> {
        return ArrayList<SelectRentDurationType>().apply {
            add(
                SelectRentDurationType(
                    option = "Vacation Rental",
                    isSelected = true,
                    tag = PropertyListTag.VACATION_RENTAL
                )
            )
        }
    }

    fun selectReasonToCancel(): ArrayList<SelectReasonToCancel> {
        return ArrayList<SelectReasonToCancel>().apply {
            add(SelectReasonToCancel(option = "Reason 1", isSelected = false))
            add(SelectReasonToCancel(option = "Reason 2", isSelected = false))
            add(SelectReasonToCancel(option = "Reason 3", isSelected = false))
            add(SelectReasonToCancel(option = "Reason 4", isSelected = false))
        }
    }

    fun timeSlotForOpenHouse(): ArrayList<ScheduleOpenHouseTiming> {
        return ArrayList<ScheduleOpenHouseTiming>().apply {
            add(ScheduleOpenHouseTiming(timeSlot = "10:30 AM - 11:30 AM", isSelected = true))
            add(ScheduleOpenHouseTiming(timeSlot = "12:00 PM - 12:30 PM"))
            add(ScheduleOpenHouseTiming(timeSlot = "11:30 AM - 12:30 AM"))
            add(ScheduleOpenHouseTiming(timeSlot = "01:30 AM - 02:00 AM"))
            add(ScheduleOpenHouseTiming(timeSlot = "02:30 AM - 05:30 AM"))
        }
    }

    fun openHousePropertyListOne(): ArrayList<RequestList> {
        return ArrayList<RequestList>().apply {
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    isDealClosed = true,
                    isScheduled = true
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    isScheduled = true
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    isDealClosed = true
                )
            )
        }
    }

    fun openHousePropertyListTwo(): ArrayList<RequestList> {
        return ArrayList<RequestList>().apply {
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    isDealClosed = true
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    isDealClosed = true
                )
            )
        }
    }

    fun openHousePropertyListThree(): ArrayList<RequestList> {
        return ArrayList<RequestList>().apply {
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    isDealClosed = true
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    isDealClosed = true
                )
            )
            add(
                RequestList(
                    userName = "John Doe Completed",
                    userEmail = "samplemai.@gmail.com",
                    userPhoneNumber = "+9 4584521611",
                    date = "23-Aug-2022",
                    time = "10:30 AM",
                    notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rutrum mollis rhoncus.",
                    propertyImage = R.drawable.dummy_image_listing,
                    propertyType = "New Apartment Nice View",
                    propertyLocation = "Quincy St, Brooklyn, NY, USA",
                    status = StatusType.FOR_SALE,
                    isDealClosed = true
                )
            )
        }
    }

    fun realEstateFavList(): ArrayList<RealEstateHomeListItem> {
        return ArrayList<RealEstateHomeListItem>().apply {
            add(
                RealEstateHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month",
                    isLiked = true
                )
            )
            add(
                RealEstateHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month",
                    isLiked = true
                )
            )
            add(
                RealEstateHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month",
                    isLiked = true
                )
            )
            add(
                RealEstateHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    title = "New Apartment Nice View",
                    status = StatusType.FOR_SALE,
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "550,000",
                    isLiked = true
                )
            )
            add(
                RealEstateHomeListItem(
                    image = R.drawable.dummy_image_listing,
                    status = StatusType.FOR_VACATION_RENT,
                    title = "New Apartment Nice View",
                    location = "Quincy St, Brooklyn, NY, USA",
                    numberOfAmenities = Amenity(
                        numberOfBeds = "4",
                        numberOfBath = "5",
                        numberOfGarage = "1",
                        numberOfSqFt = "1200"
                    ),
                    amount = "7,500/Month",
                    isLiked = true
                )
            )
        }
    }

    fun selectedUserType(): ArrayList<SelectedUserType> {
        return ArrayList<SelectedUserType>().apply {
            add(SelectedUserType(option = "User", isSelected = false))
            add(SelectedUserType(option = "Real Estate", isSelected = false))
            add(SelectedUserType(option = "Business", isSelected = false))
        }
    }

    fun subsCard(): ArrayList<SubscriptionCard> {
        return ArrayList<SubscriptionCard>().apply {
            add(
                SubscriptionCard(
                    title = "Silver",
                    price = "99",
                    timePeriod = "1 Month",
                    isSelected = true
                )
            )
            add(SubscriptionCard(title = "Gold", price = "599", timePeriod = "6 Months"))
            add(SubscriptionCard(title = "Platinum", price = "999", timePeriod = "1 Year"))
        }
    }

    fun subsDesc(): ArrayList<String> {
        return ArrayList<String>().apply {
            add("Lorem ipsum dolor sit")
            add("Lorem ipsum dolor sit")
            add("Lorem ipsum dolor sit")
            add("Lorem ipsum dolor sit")
            add("Lorem ipsum dolor sit")
            add("Lorem ipsum dolor sit")
            add("Lorem ipsum dolor sit")
        }
    }

    fun businessHomeChip(): ArrayList<RealEstateOrBusinessHomeChip> {
        return ArrayList<RealEstateOrBusinessHomeChip>().apply {
            add(
                RealEstateOrBusinessHomeChip(
                    icon = R.drawable.ic_shake_hands_red,
                    title = "Business Profile",
                    color = R.color.C_ED1D26,
                    chip = RealEstateOrBusinessChipType.BUSINESS_PROFILE
                )
            )
            add(
                RealEstateOrBusinessHomeChip(
                    icon = R.drawable.ic_add_or_edit_deals,
                    title = "Add/edit Deals",
                    color = R.color.C_00A9DE,
                    chip = RealEstateOrBusinessChipType.ADD_OR_EDIT_DEALS
                )
            )
            add(
                RealEstateOrBusinessHomeChip(
                    icon = R.drawable.ic_chat_purple,
                    title = "Chat",
                    color = R.color.C_C361FF,
                    badgeCount = 10,
                    chip = RealEstateOrBusinessChipType.CHAT
                )
            )
        }
    }

    fun businessList(): ArrayList<BusinessHomeListItem> {
        return ArrayList<BusinessHomeListItem>().apply {
            add(
                BusinessHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.FEATURED,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                BusinessHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                BusinessHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
            add(
                BusinessHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15"
                )
            )
        }
    }

    fun businessNavigationDrawerOption(): ArrayList<NavigationDrawerOption> {
        return ArrayList<NavigationDrawerOption>().apply {
            add(
                NavigationDrawerOption(
                    option = " Change Password",
                    icon = R.drawable.ic_lock,
                    onClick = OnClick.CHANGE_PASSWORD
                )
            )
            add(
                NavigationDrawerOption(
                    option = "My Business Profile",
                    icon = R.drawable.ic_business_profile,
                    onClick = OnClick.MY_BUSINESS_PROFILE
                )
            )
            add(
                NavigationDrawerOption(
                    option = "My Deals",
                    icon = R.drawable.ic_heart_selected_small,
                    onClick = OnClick.MY_FAV_DEALS
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Delete Account",
                    icon = R.drawable.ic_delete_acc,
                    onClick = OnClick.DELETE_ACCOUNT
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Rate App",
                    icon = R.drawable.ic_star,
                    OnClick.RATE_APP
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Share App",
                    icon = R.drawable.ic_share,
                    OnClick.SHARE_APP
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Help & FAQ",
                    icon = R.drawable.ic_faq,
                    onClick = OnClick.HELP_AND_FAQ
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Terms & Privacy Policy",
                    icon = R.drawable.ic_terms_and_pp,
                    onClick = OnClick.TERMS_AND_PRIVACY_POLICY
                )
            )
            add(
                NavigationDrawerOption(
                    option = "About Us",
                    icon = R.drawable.ic_about_us,
                    onClick = OnClick.ABOUT_US
                )
            )
            add(
                NavigationDrawerOption(
                    option = "Contact Us",
                    icon = R.drawable.ic_contact_us,
                    onClick = OnClick.CONTACT_US
                )
            )
        }
    }


    fun businessFavList(): ArrayList<BusinessHomeListItem> {
        return ArrayList<BusinessHomeListItem>().apply {
            add(
                BusinessHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.FEATURED,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15",
                    isLiked = true
                )
            )
            add(
                BusinessHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15",
                    isLiked = true
                )
            )
            add(
                BusinessHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15",
                    isLiked = true
                )
            )
            add(
                BusinessHomeListItem(
                    image = R.drawable.dummy_image_automotive,
                    status = StatusType.NONE,
                    title = "Car Washing",
                    location = "Quincy St, Brooklyn, NY, USA",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    amount = "15",
                    isLiked = true
                )
            )
        }
    }

    fun selectBusinessTimeSlots(): ArrayList<SelectBusinessTimeSlots> {
        return ArrayList<SelectBusinessTimeSlots>().apply {
            add(
                SelectBusinessTimeSlots(
                    isChecked = false,
                    weekDay = "Sunday",
                    listOfTimeSlots = ArrayList<SelectTimeSlots>().apply {
                        add(
                            SelectTimeSlots(
                                startTime = "00:00 AM",
                                endTime = "00:00 PM"
                            )
                        )
                    })
            )
            add(
                SelectBusinessTimeSlots(
                    isChecked = false,
                    weekDay = "Monday",
                    listOfTimeSlots = ArrayList<SelectTimeSlots>().apply {
                        add(
                            SelectTimeSlots(
                                startTime = "00:00 AM",
                                endTime = "00:00 PM"
                            )
                        )
                    })
            )
            add(
                SelectBusinessTimeSlots(
                    isChecked = false,
                    weekDay = "Tuesday",
                    listOfTimeSlots = ArrayList<SelectTimeSlots>().apply {
                        add(
                            SelectTimeSlots(
                                startTime = "00:00 AM",
                                endTime = "00:00 PM"
                            )
                        )
                    })
            )
            add(
                SelectBusinessTimeSlots(
                    isChecked = false,
                    weekDay = "Wednesday",
                    listOfTimeSlots = ArrayList<SelectTimeSlots>().apply {
                        add(
                            SelectTimeSlots(
                                startTime = "00:00 AM",
                                endTime = "00:00 PM"
                            )
                        )
                    })
            )
            add(
                SelectBusinessTimeSlots(
                    isChecked = false,
                    weekDay = "Thursday",
                    listOfTimeSlots = ArrayList<SelectTimeSlots>().apply {
                        add(
                            SelectTimeSlots(
                                startTime = "00:00 AM",
                                endTime = "00:00 PM"
                            )
                        )
                    })
            )
            add(
                SelectBusinessTimeSlots(
                    isChecked = false,
                    weekDay = "Friday",
                    listOfTimeSlots = ArrayList<SelectTimeSlots>().apply {
                        add(
                            SelectTimeSlots(
                                startTime = "00:00 AM",
                                endTime = "00:00 PM"
                            )
                        )
                    })
            )
            add(
                SelectBusinessTimeSlots(isChecked = false, weekDay = "Saturday",
                    listOfTimeSlots = ArrayList<SelectTimeSlots>().apply {
                        add(
                            SelectTimeSlots(
                                startTime = "00:00 AM",
                                endTime = "00:00 PM"
                            )
                        )
                    })
            )
        }
    }

    fun setDefaultTimeSlots(): SelectTimeSlots {
        return SelectTimeSlots(startTime = "00:00 AM", endTime = "00:00 PM")
    }

    fun setDealButtons(): ArrayList<BusinessDealButton> {
        return ArrayList<BusinessDealButton>().apply {
            add(
                BusinessDealButton(
                    icon = R.drawable.ic_tick_outlined_bg,
                    title = "Active",
                    tag = OnClick.ACTIVE
                )
            )
            add(
                BusinessDealButton(
                    icon = R.drawable.ic_pause,
                    title = "Pause",
                    tag = OnClick.PAUSE
                )
            )
            add(
                BusinessDealButton(
                    icon = R.drawable.ic_edit_orange,
                    title = "Edit",
                    tag = OnClick.EDIT
                )
            )
            add(
                BusinessDealButton(
                    icon = R.drawable.ic_delete_outlined,
                    title = "Delete",
                    tag = OnClick.DELETE
                )
            )
        }
    }

    fun setDealList(): ArrayList<BusinessDeal> {
        return ArrayList<BusinessDeal>().apply {
            add(
                BusinessDeal(
                    dealName = "Deal Name",
                    discount = "20% - 30%",
                    startSaleDate = "25 Dec 2022",
                    startSaleTime = "12:00 PM",
                    endSaleDate = "05 Jan 2023",
                    endSaleTime = "12:00 AM",
                    views = "101",
                    redeems = "45",
                    shares = "10"
                )
            )
            add(
                BusinessDeal(
                    dealName = "Deal Name",
                    discount = "20% - 30%",
                    startSaleDate = "25 Dec 2022",
                    startSaleTime = "12:00 PM",
                    endSaleDate = "05 Jan 2023",
                    endSaleTime = "12:00 AM",
                    views = "101",
                    redeems = "45",
                    shares = "10"
                )
            )
            add(
                BusinessDeal(
                    dealName = "Deal Name",
                    discount = "20% - 30%",
                    startSaleDate = "25 Dec 2022",
                    startSaleTime = "12:00 PM",
                    endSaleDate = "05 Jan 2023",
                    endSaleTime = "12:00 AM",
                    views = "101",
                    redeems = "45",
                    shares = "10"
                )
            )
        }
    }

    fun selectTypeOfDeal(): ArrayList<SelectTypeOfDeal> {
        return ArrayList<SelectTypeOfDeal>().apply {
            add(
                SelectTypeOfDeal(
                    option = "Link deal to my shopping cart",
                    isSelected = false,
                    tag = TypeOfDeals.LINK_DEAL_TO_MY_SHOPPING_CART
                )
            )
            add(
                SelectTypeOfDeal(
                    option = "Pay at the point of sale",
                    isSelected = false,
                    tag = TypeOfDeals.PAY_AT_THE_POINT_OF_SALE
                )
            )
        }
    }

    fun listOfDates(): ArrayList<String> {
        return ArrayList<String>().apply {
            add("17 May 2023")
            add("20 May 2023")
            add("22 May 2023")
            add("23 May 2023")
            add("25 May 2023")
        }
    }

    fun imageLibAutomativeTab(): ArrayList<ImageLibrary> {
        return ArrayList<ImageLibrary>().apply {
            repeat(15) {
                add(
                    ImageLibrary(
                        profileImage = R.drawable.dummy_salon_one,
                        profileName = "Automative Image Name 1"
                    )
                )
            }
        }
    }

    fun imageLibFoodTab(): ArrayList<ImageLibrary> {
        return ArrayList<ImageLibrary>().apply {
            repeat(15) {
                add(
                    ImageLibrary(
                        profileImage = R.drawable.dummy_salon_two,
                        profileName = "Food Image Name 1"
                    )
                )
            }
        }
    }

    fun imageLibHealthTab(): ArrayList<ImageLibrary> {
        return ArrayList<ImageLibrary>().apply {
            repeat(15) {
                add(
                    ImageLibrary(
                        profileImage = R.drawable.dummy_salon_three,
                        profileName = "Health Image Name 1"
                    )
                )
            }
        }
    }

    fun imageLibSalonTab(): ArrayList<ImageLibrary> {
        return ArrayList<ImageLibrary>().apply {
            repeat(10) {
                add(
                    ImageLibrary(
                        profileImage = R.drawable.dummy_salon_one,
                        profileName = "Image Name 1"
                    )
                )
                add(
                    ImageLibrary(
                        profileImage = R.drawable.dummy_salon_two,
                        profileName = "Image Name 1"
                    )
                )
                add(
                    ImageLibrary(
                        profileImage = R.drawable.dummy_salon_three,
                        profileName = "Image Name 1"
                    )
                )
            }
        }
    }

    fun contactOptions(): ArrayList<ContactOptions> {
        return ArrayList<ContactOptions>().apply {
            add(
                ContactOptions(
                    option = R.drawable.ic_phone,
                    optionName = "Phone",
                    tag = OnClick.PHONE
                )
            )
            add(ContactOptions(option = R.drawable.ic_phone, optionName = "SMS", tag = OnClick.SMS))
            add(
                ContactOptions(
                    option = R.drawable.ic_email,
                    optionName = "Email",
                    tag = OnClick.EMAIL
                )
            )
        }
    }
}