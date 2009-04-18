class TopicColor {

	String color
	
    static constraints = {
		color(validator: {val,obj ->
			return val ==~ /[0-9a-fA-F]{6}/
		})
    }
}
