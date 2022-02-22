from django.db import models


# Create your models here.
class Board(models.Model):
    bno = models.IntegerField()
    title = models.TextField()
    contents = models.TextField()
