import django.contrib.admin
from django.contrib import admin

from app.models import Profile, Question, Answer, Tag, Like


admin.site.register(Profile)
admin.site.register(Question)
admin.site.register(Answer)
admin.site.register(Tag)
admin.site.register(Like)