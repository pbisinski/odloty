package git.pbisinski.odloty.di

import git.pbisinski.odloty.api.datasource.SkyScannerDataSource
import git.pbisinski.odloty.api.repository.LocationRepository
import git.pbisinski.odloty.api.repository.LocationRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

inline fun <reified T> createWebService(retrofit: Retrofit): T =
  retrofit.create(T::class.java)

val repositoryModule = module {
  single<SkyScannerDataSource> { createWebService(retrofit = get()) }
  single<LocationRepository> { LocationRepositoryImpl(dataSource = get()) }
}
