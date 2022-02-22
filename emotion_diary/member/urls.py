from django.urls import path, include

from .views import main, RegistrationAPI, LoginAPI, UserAPI, ProfileUpdateAPI

from knox import views as knox_views

urlpatterns = [
    path('', main),
    path("auth/register/", RegistrationAPI.as_view()),
    path("auth/login/", LoginAPI.as_view()),
    path("auth/user/", UserAPI.as_view()),

    path('auth/logout/', knox_views.LogoutView.as_view(), name='knox_logout'),
    path('auth/update/', ProfileUpdateAPI.as_view())


]
