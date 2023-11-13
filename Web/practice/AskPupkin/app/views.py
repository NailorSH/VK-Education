from django.shortcuts import render


def index(request):
    questions = [
        {
            'id': i,
            'title': f'Question {i}',
            'content': f'''Lorem Ipsum is simply dummy text of the printing and typesetting
            industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
            when an unknown printer took a galley of type and scrambled it to make a type specimen
            book.'''
        } for i in range(10)
    ]

    return render(request, 'index.html', {'questions': questions})


def question(request):
    answers = [
        {
            'id': i,
            'content': f'''First of all I would like to thank you for the invitation to participate in such a
                Russia is the huge territory which in many respects needs to be render habitable. {i}'''
        } for i in range(10)
    ]
    return render(request, 'question.html', {'answers': answers})
