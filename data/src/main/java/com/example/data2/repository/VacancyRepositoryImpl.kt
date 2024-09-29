package com.example.data2.repository

import com.example.data2.storage.model.vacancy.Address
import com.example.data2.storage.model.vacancy.Button
import com.example.data2.storage.model.vacancy.Experience
import com.example.data2.storage.model.vacancy.Offer
import com.example.data2.storage.model.vacancy.Offers
import com.example.data2.storage.model.vacancy.Salary
import com.example.data2.storage.model.vacancy.Vacancy
import com.example.data2.storage.services.NetworkService
import com.example.data2.storage.vacancyStorage.VacancyStorage
import com.example.domain.model.vacancy.AddressDomain
import com.example.domain.model.vacancy.ButtonDomain
import com.example.domain.model.vacancy.ExperienceDomain
import com.example.domain.model.vacancy.OfferDomain
import com.example.domain.repository.VacancyRepository
import com.example.domain.model.vacancy.OffersDomain
import com.example.domain.model.vacancy.SalaryDomain
import com.example.domain.model.vacancy.VacancyDomain
import javax.inject.Inject


class VacancyRepositoryImpl @Inject constructor(private val vacancyStorage: VacancyStorage): VacancyRepository {

    override suspend fun getVacancies(): OffersDomain {
        val res = vacancyStorage.getVacancies()
        return mapToOffersDomain(res)
    }

    private fun mapToOffersDomain(offers: Offers): OffersDomain {
        return OffersDomain(
            offers = offers.offers.map{ mapToOfferDomain(it) },
            vacancies = offers.vacancies.map { mapToVacancyDomain(it) })
    }

    private fun mapToOfferDomain(offer: Offer): OfferDomain {
        return OfferDomain(
            button = mapToButtonDomain(offer.button),
            link = offer.link,
            id = offer.id,
            title = offer.title)
    }
    private fun mapToButtonDomain(button: Button?): ButtonDomain? {
        return if(button == null){
            null
        } else {
            ButtonDomain(text = button.text)
        }
    }
    private fun mapToVacancyDomain(vacancy: Vacancy): VacancyDomain {
        return VacancyDomain(
            address = mapToAddressDomain(vacancy.address),
            company = vacancy.company,
            experience = mapToExperienceDomain(vacancy.experience),
            id = vacancy.id,
            isFavorite = vacancy.isFavorite,
            publishedDate = vacancy.publishedDate,
            questions = vacancy.questions,
            salary = mapToSalaryDomain(vacancy.salary),
            schedules = vacancy.schedules,
            title = vacancy.title,
            responsibilities = vacancy.responsibilities,
            description = vacancy.description,
            lookingNumber = vacancy.lookingNumber,
            appliedNumber = vacancy.appliedNumber
        )
    }
    private fun mapToAddressDomain(address: Address): AddressDomain {
        return AddressDomain(
            house = address.house,
            street = address.street,
            town = address.town
        )
    }
    private fun mapToExperienceDomain(experience: Experience): ExperienceDomain{
        return ExperienceDomain(
            previewText = experience.previewText,
            text = experience.text
        )
    }
    private fun mapToSalaryDomain(salary: Salary): SalaryDomain {
        return SalaryDomain(
            full = salary.full,
            short = salary.short
        )
    }

    private fun mapToOffers(offersDomain: OffersDomain): Offers {
        return Offers(
            offers = offersDomain.offers.map{ mapToOffer(it) },
            vacancies = offersDomain.vacancies.map { mapToVacancy(it) })
    }

    private fun mapToOffer(offerDomain: OfferDomain): Offer {
        return Offer(
            button = mapToButton(offerDomain.button),
            link = offerDomain.link,
            id = offerDomain.id,
            title = offerDomain.title)
    }
    private fun mapToButton(buttonDomain: ButtonDomain?): Button? {
        return if(buttonDomain == null){
            null
        } else {
            Button(text = buttonDomain.text)
        }
    }
    private fun mapToVacancy(vacancyDomain: VacancyDomain): Vacancy {
        return Vacancy(
            address = mapToAddress(vacancyDomain.address),
            company = vacancyDomain.company,
            experience = mapToExperience(vacancyDomain.experience),
            id = vacancyDomain.id,
            isFavorite = vacancyDomain.isFavorite,
            publishedDate = vacancyDomain.publishedDate,
            questions = vacancyDomain.questions,
            salary = mapToSalary(vacancyDomain.salary),
            schedules = vacancyDomain.schedules,
            title = vacancyDomain.title)
    }
    private fun mapToAddress(addressDomain: AddressDomain): Address {
        return Address(
            house = addressDomain.house,
            street = addressDomain.street,
            town = addressDomain.town
        )
    }
    private fun mapToExperience(experienceDomain: ExperienceDomain): Experience{
        return Experience(
            previewText = experienceDomain.previewText,
            text = experienceDomain.text
        )
    }
    private fun mapToSalary(salaryDomain: SalaryDomain): Salary {
        return Salary(
            full = salaryDomain.full,
            short = salaryDomain.short
        )
    }
}