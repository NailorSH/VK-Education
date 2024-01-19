from django.contrib.contenttypes.models import ContentType
from django.core.management.base import BaseCommand
from django.contrib.auth.models import User
from django.utils.crypto import get_random_string
from faker import Faker
from random import randint, sample
from app.models import Profile, Question, Answer, Tag, Like

fake = Faker()


class Command(BaseCommand):
    help = 'Fill the database with test data'

    def add_arguments(self, parser):
        parser.add_argument('ratio', type=int, help='Ratio for data creation')

    def handle(self, *args, **options):
        ratio = options['ratio']

        self.create_users(ratio)
        self.create_tags(ratio)
        self.create_questions(ratio)
        self.create_answers(ratio)
        self.create_likes(ratio)

        self.stdout.write(self.style.SUCCESS(f'Successfully filled the database with test data (Ratio: {ratio})'))

    def create_users(self, ratio):
        for i in range(ratio):
            user = User.objects.create_user(username=f'user_{i}', password=f'password_{i}')
            Profile.objects.create(user=user)

    def create_tags(self, ratio):
        for i in range(ratio):
            Tag.objects.create(tag_name=f'tag_{i}')

    def create_questions(self, ratio):
        for i in range(ratio * 10):
            user = Profile.objects.get(user__username=f'user_{randint(0, ratio - 1)}')
            title = fake.sentence()
            content = fake.paragraph()
            tags = sample(list(Tag.objects.all()), randint(1, 5))
            question = Question.objects.create(author=user, title=title, content=content)
            question.tags.add(*tags)

    def create_answers(self, ratio):
        for i in range(ratio * 100):
            user = Profile.objects.get(user__username=f'user_{randint(0, ratio - 1)}')
            question = Question.objects.get(pk=randint(1, ratio * 10))
            text = fake.paragraph()
            accepted = fake.boolean()
            Answer.objects.create(author=user, question=question, text=text, accepted=accepted)

    def create_likes(self, ratio):
        for i in range(ratio * 200):
            user = User.objects.get(username=f'user_{randint(0, ratio - 1)}')
            content_type = ContentType.objects.get_for_model(Question if fake.boolean() else Answer)
            object_id = randint(1, ratio * 10 + ratio * 100)
            Like.objects.create(user=user, content_type=content_type, object_id=object_id)
