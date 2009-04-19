

class ConfigurationController {
    
    def index = {
    	redirect(url: g.createLink(action:list,params:params,absolute:true))
    }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ configurationInstanceList: Configuration.list( params ), configurationInstanceTotal: Configuration.count() ]
    }

    def show = {
        def configurationInstance = Configuration.get( params.id )

        if(!configurationInstance) {
            flash.message = "Configuration not found with id ${params.id}"
            redirect(url: g.createLink(action:list,absolute:true))
        }
        else { return [ configurationInstance : configurationInstance ] }
    }

    def delete = {
        def configurationInstance = Configuration.get( params.id )
        if(configurationInstance) {
            try {
                configurationInstance.delete()
                flash.message = "Configuration ${params.id} deleted"
                redirect(url: g.createLink(action:list,absolute:true))
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Configuration ${params.id} could not be deleted"
                redirect(url: g.createLink(action:show,id:params.id,absolute:true))
            }
        }
        else {
            flash.message = "Configuration not found with id ${params.id}"
            redirect(url: g.createLink(action:list,absolute:true))
        }
    }

    def edit = {
        def configurationInstance = Configuration.get( params.id )

        if(!configurationInstance) {
            flash.message = "Configuration not found with id ${params.id}"
            redirect(url: g.createLink(action:list,absolute:true))
        }
        else {
            return [ configurationInstance : configurationInstance ]
        }
    }

    def update = {
        def configurationInstance = Configuration.get( params.id )
        if(configurationInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(configurationInstance.version > version) {
                    
                    configurationInstance.errors.rejectValue("version", "configuration.optimistic.locking.failure", "Another user has updated this Configuration while you were editing.")
                    render(view:'edit',model:[configurationInstance:configurationInstance])
                    return
                }
            }
            configurationInstance.properties = params
            if(!configurationInstance.hasErrors() && configurationInstance.save()) {
                flash.message = "Configuration ${params.id} updated"
                redirect(url: g.createLink(action:show,id:configurationInstance.id,absolute:true))
            }
            else {
                render(view:'edit',model:[configurationInstance:configurationInstance])
            }
        }
        else {
            flash.message = "Configuration not found with id ${params.id}"
            redirect(url: g.createLink(action:edit,id:params.id,absolute:true))
        }
    }

    def create = {
        def configurationInstance = new Configuration()
        configurationInstance.properties = params
        return ['configurationInstance':configurationInstance]
    }

    def save = {
        def configurationInstance = new Configuration(params)
        if(!configurationInstance.hasErrors() && configurationInstance.save()) {
            flash.message = "Configuration ${configurationInstance.id} created"
            redirect(url: g.createLink(action:show,id:configurationInstance.id,absolute:true))
        }
        else {
            render(view:'create',model:[configurationInstance:configurationInstance])
        }
    }
}
