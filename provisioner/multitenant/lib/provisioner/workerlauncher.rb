#!/usr/bin/env ruby
# encoding: UTF-8
#
# Copyright 2012-2014, Continuuity, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


# simple class to construct the ruby command used to launch a worker process
module Loom
  class WorkerLauncher
    attr_accessor :tenant, :provisioner, :name, :options

    def initialize(options)
      @options = options || {}
    end

    def cmd
      cmd = "#{File.join(RbConfig::CONFIG['bindir'], RbConfig::CONFIG['ruby_install_name'])}"
      cmd += " #{File.dirname(__FILE__)}/../../../daemon/provisioner.rb"
      cmd += " --uri #{@options[:uri]}" if @options[:uri]
      cmd += " --log-directory #{@options[:log_directory]}" if @options[:log_directory]
      cmd += " --log-level #{@options[:log_level]}" if @options[:log_level]
      cmd += " --provisioner #{@provisioner}" unless @provisioner.nil?
      cmd += " --tenant #{@tenant}" unless @tenant.nil?
      cmd += " --name #{@name}" unless @name.nil?
      cmd
    end

    def test_cmd
      cmd = "#{File.join(RbConfig::CONFIG['bindir'], RbConfig::CONFIG['ruby_install_name'])}"
      cmd += " #{File.dirname(__FILE__)}/../../spec/worker.rb"
      cmd += " #{@name}" unless @name.nil?
      cmd
    end
  end
end